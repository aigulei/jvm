package com.ai.jvm.condition;

import java.util.concurrent.TimeUnit;

public class WaitAndNotify {
	private volatile int signal;
	
	public synchronized void set() {
		this.signal = 1;
		notifyAll();
	}
	
	public synchronized int get() {
		System.out.println(Thread.currentThread().getName()+"方法執行了");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(signal !=1 ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+"方法執行完成");
		return signal;
	}
	public static void main(String[] args) {
		WaitAndNotify waitAndNotify = new WaitAndNotify();
		Target1 target1 = new Target1(waitAndNotify);
		Target2 target2 = new Target2(waitAndNotify);
		new Thread(target2).start();
		new Thread(target2).start();
		new Thread(target2).start();
		new Thread(target2).start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(target1).start();
	}
}
