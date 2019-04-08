package com.ai.jvm.condition;

public class WaitAndNotifyCommon {
	private volatile int signal;
	
	public void set(int value) {
		this.signal = value;
	}
	
	public int get() {
		return signal;
	}
	
	public static void main(String[] args) {
		WaitAndNotifyCommon waitAndNotifyDemo = new WaitAndNotifyCommon();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized(waitAndNotifyDemo) {
					System.out.println("修改状态的线程执行。。。");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					waitAndNotifyDemo.set(1);
					System.out.println("修改状态的线程完成");
					waitAndNotifyDemo.notify();
				}
					
			}
		}) .start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				synchronized (waitAndNotifyDemo) {
					// 等待singal为1开始执行，否则不能执行
					while (waitAndNotifyDemo.get() != 1) {
						try {
							waitAndNotifyDemo.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("模拟代码执行");
				}
				
			}
		}).start();
	}
}
