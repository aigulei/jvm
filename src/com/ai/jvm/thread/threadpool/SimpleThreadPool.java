package com.ai.jvm.thread.threadpool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 自定义线程池
 */
public class SimpleThreadPool extends Thread{
    private int size;
    private final int taskQueueSize;
    private final static int DEFAULT_SIZE = 10;
    private final static int DEFAULT_MAX_SIZE = 2000;
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";
    private static volatile int seq = 0;
    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");
    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();
    private  DiscardPolicy discardPolicy;
    private volatile  boolean destroy = false;
    public SimpleThreadPool() {
        this(4,8,12,DEFAULT_MAX_SIZE,()->{
            throw new RuntimeException("任务已满");
        });
    }

    public SimpleThreadPool(int min,int active,int max,int taskQueueSize,DiscardPolicy discardPolicy) {
        this.min = min;
        this.active =active;
        this.max = max;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        for(int i = 0;i<this.min;i++){
            createWorkTask();
        }
        this.size = min;
        this.start();
    }

    public int getSize() {
        return size;
    }

    public int getTaskQueueSize() {
        return taskQueueSize;
    }

    public boolean isDestroy(){
        return this.destroy;
    }

    private int min;
    private int max;
    private int active;

    @Override
    public void run() {
        while (!destroy){
            System.out.printf("Pool#Min:%d,Acive:%d,Max:%d,Current:%d,QueueSize:%d\n",
                    this.min,this.active,this.max,this.size,TASK_QUEUE.size());
            try {
                Thread.sleep(5000);
                if(TASK_QUEUE.size() > active && size<active){
                    for(int i = size;i<active;i++){
                        createWorkTask();
                    }
                    System.out.println("The pool increment to active");
                    this.size = active;
                }else if(TASK_QUEUE.size()>max && size<max){
                    for(int i =size;i<max;i++){
                        createWorkTask();
                    }
                    System.out.println("The pool increment to max");
                    this.size = max;
                }

                synchronized (THREAD_QUEUE) {
                    if(TASK_QUEUE.isEmpty() && size>active){
                        int releaseSize = size - active;
                        System.out.println("還需關閉...."+releaseSize);
                        Iterator<WorkerTask> iterator = THREAD_QUEUE.iterator();
                        while(iterator.hasNext()){
                            WorkerTask next = iterator.next();
                            if(next.getTaskState()==TaskState.BLOCKED){
                                next.close();
                                next.interrupt();
                                iterator.remove();
                                size--;
                            }

                            //System.out.println("THREADS..."+next.getName()+"....."+next.getTaskState());
                        }

                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void submit(Runnable runnable){
        if(destroy)
            throw  new IllegalStateException("the thread pool already destroy and not allow submit");
        synchronized (TASK_QUEUE){
            if(TASK_QUEUE.size()> taskQueueSize){
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }
    public interface DiscardPolicy{
        void discard();
    }
    private void createWorkTask(){
        WorkerTask workerTask = new WorkerTask(GROUP,THREAD_PREFIX+(seq++));
        workerTask.start();
        THREAD_QUEUE.add(workerTask);
    }

    public void shutdown() throws InterruptedException {
        synchronized (THREAD_QUEUE){
            while (!TASK_QUEUE.isEmpty()){
                Thread.sleep(50);
            }
            int initVal = THREAD_QUEUE.size();
            while (initVal>0){
                for (WorkerTask workerTask:THREAD_QUEUE){
                    if(workerTask.getTaskState()==TaskState.BLOCKED){
                        workerTask.interrupt();
                        workerTask.close();
                        initVal--;
                    }else{
                        Thread.sleep(10);

                    }
                }
            }
            this.destroy = true;
            System.out.println("The thread pool disposed");
        }

    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD;
    }

    private static class WorkerTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;

        public TaskState getTaskState() {
            return this.taskState;
        }

        public WorkerTask(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"..12345678..."+this.taskState);
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable = null;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            this.taskState = TaskState.BLOCKED;
                            System.out.println(Thread.currentThread().getName()+"的状态为....:"+this.taskState);
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            System.out.println("close");
                            return;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();

                }
                if(runnable!=null){
                    this.taskState = TaskState.RUNNING;
                    runnable.run();
                    this.taskState = TaskState.FREE;
                }

            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        IntStream.range(0,40)
                .forEach(i ->{
                    threadPool.submit(()->{
                        System.out.println("The runnable "+i+" be service by "+Thread.currentThread().getName()+" start");
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("The runnable "+i+" be service by "+Thread.currentThread().getName()+" finished");

                    });
                });

        Thread.sleep(30000);
        for (WorkerTask workerTask:THREAD_QUEUE){
            System.out.println("123..."+Thread.currentThread().getName()+"..."+workerTask.getName()+"..."+workerTask.getTaskState()+"...123");
        }
        threadPool.shutdown();
    }
}
