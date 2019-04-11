package com.ai.jvm.juc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExample {
	private static final LinkedList<Object> OBJECTS = new LinkedList<Object>();
	private static final int MAX_COUNT = 5;
	private static final ArrayBlockingQueue<String> BLOCKING_QUEUE= new ArrayBlockingQueue<>(1);
	public static Thread createThread(String name) {
		return new Thread(()->{synchronized (OBJECTS) {
			System.out.println(Thread.currentThread().getName()+"开始执行");
			while(OBJECTS.size()>5) {
				try {
					OBJECTS.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			OBJECTS.addLast(new Object());
		}
		try {
			System.out.println("任务执行");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (OBJECTS) {
			System.out.println(Thread.currentThread().getName()+"执行完毕");

			OBJECTS.removeFirst();
			OBJECTS.notifyAll();
		}},name);
	}
	public static void c(CountDownLatch countDownLatch) {
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(()->{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Hello c");
			countDownLatch.countDown();
		});
		service.shutdown();
		
	}
	public static void d() {
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(()->{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Hello d");
		});
		service.shutdown();

	}
	
	public static void e() {
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(()->{
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Hello e");
			BLOCKING_QUEUE.add("e");
		});
		service.shutdown();
		
	}
	public static void f() {
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(()->{
			try {
				String f = BLOCKING_QUEUE.take();
				System.out.println("Hello "+f+"adsfa");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		});
		service.shutdown();

	}
	
	public static void main(String[] args) throws InterruptedException {
		f();
		e();
//		CountDownLatch countDownLatch = new CountDownLatch(1);
//		c(countDownLatch);
//		countDownLatch.await();
//		d();
		
//		List<Thread> lists = new ArrayList<Thread>();
//		for(int i = 0;i<10;i++) {
//			Thread thread = createThread("Thread..."+i);
//			thread.start();
//			lists.add(thread);
//		}
//		lists.forEach(thread ->{
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		});
//		System.out.println("all task complete....");
	
	}
}
