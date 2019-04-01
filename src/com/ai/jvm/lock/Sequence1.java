package com.ai.jvm.lock;

import java.util.concurrent.locks.Lock;

public class Sequence1 {
	private static int value;
	Lock lock = new MyLock();
	
	public int getSequence() {
		lock.lock();
		value = value +1;
		lock.unlock();
		return value;
	}
	
	public static void main(String[] args) {
		Sequence1 sequence = new Sequence1();
		new Thread(()->{
			while(true) {
				System.out.println(Thread.currentThread()+"..."+sequence.getSequence());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}) .start();
		
		new Thread(()->{
			while(true) {
				System.out.println(Thread.currentThread()+"..."+sequence.getSequence());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}) .start();
	}
}
