package com.ai.jvm.juc;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
	public Semaphore semaphore ;
	public SemaphoreDemo(Semaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	public void a() {
		try {
			semaphore.acquire();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaphore.release();
		}
		System.out.println(Thread.currentThread().getName());
	}
	
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(2);
		SemaphoreDemo semaphoreDemo = new SemaphoreDemo(semaphore);
		new Thread(() ->{semaphoreDemo.a();}).start();
		new Thread(() ->{semaphoreDemo.a();}).start();
		new Thread(() ->{semaphoreDemo.a();}).start();
	}
}
