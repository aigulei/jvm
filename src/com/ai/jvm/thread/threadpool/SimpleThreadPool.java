package com.ai.jvm.thread.threadpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 自定义线程池
 */
public class SimpleThreadPool {
    private final int size;
    private final static int DEFAULT_SIZE = 10;
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";
    private static volatile int seq = 0;
    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");
    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();
    public SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.size = size;
        init();
    }

    private void init() {
        for(int i = 0;i<size;i++){
            createWorkTask();
        }
    }

    public void submit(Runnable runnable){
        synchronized (TASK_QUEUE){
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private void createWorkTask(){
        WorkerTask workerTask = new WorkerTask(GROUP,THREAD_PREFIX+(seq++));
        workerTask.start();
        THREAD_QUEUE.add(workerTask);
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
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable = null;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            this.taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
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

    public static void main(String[] args) {
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
        System.out.println("----------------");
    }
}
