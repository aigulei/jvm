package com.ai.jvm.command;

import java.util.concurrent.TimeUnit;


/**
 * 	死锁Example
 * @author Administrator
 *
 */
public class DeadLockExample implements Runnable {
	private final  Object o1;
	private final Object o2;
	public DeadLockExample(Object o1,Object o2) {
		this.o1 = o1;
		this.o2 = o2;
	}
	@Override
	public void run() {
		synchronized (o1) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (o2) {
				System.out.println("Hello...");
			}
		}
	}
	public static void main(String[] args) {
		Object o1 = new Object();
		Object o2 = new Object();
		DeadLockExample d1 = new DeadLockExample(o1, o2);
		DeadLockExample d2 = new DeadLockExample(o2, o1);
		new Thread(d1,"D1 name").start();
		new Thread(d2,"D2 name").start();
	}
	
}
