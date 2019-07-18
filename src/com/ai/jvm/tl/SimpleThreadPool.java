package com.ai.jvm.tl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleThreadPool {
	
	private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<Runnable>();
	
	private static final List<WorkerThread> THREAD_POOL = new ArrayList<WorkerThread>();
	
	
	
	public SimpleThreadPool() {
		for(int i=0;i<10;i++) {
			WorkerThread workerThread = new WorkerThread("SIMPLE_THREAD_POOL_"+i);
			workerThread.start();
			THREAD_POOL.add(workerThread);
		}
	}
	
	
	public void submit(Runnable runnable) {
		synchronized (TASK_QUEUE) {
			TASK_QUEUE.addFirst(runnable);
			TASK_QUEUE.notifyAll();
		}
	}
	
	private class WorkerThread extends Thread{
		public WorkerThread(String name) {
			super(name);
		}
		
		private ThreadState threadState = ThreadState.FREE;
		@Override
		public void run() {
			while(true) {
				Runnable runnable = null;
				synchronized (TASK_QUEUE) {
					if(TASK_QUEUE.isEmpty()) {
						try {
							TASK_QUEUE.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					runnable = TASK_QUEUE.removeLast();
				}
				if (runnable!=null) {
					threadState = ThreadState.RUNNING;
					runnable.run();
					threadState = ThreadState.FREE;
				}
			}
		}
	}
	
	private enum ThreadState{
		FREE,RUNNING,BLOCKED,DEAD;
	}
	public static void main(String[] args) {
		SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
		IntStream.rangeClosed(0,40).forEach(i ->{
			simpleThreadPool.submit(()->{
				System.out.println(Thread.currentThread().getName()+"...begin");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"...end");
			});
		});
	}
}
