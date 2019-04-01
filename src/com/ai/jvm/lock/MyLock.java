package com.ai.jvm.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * 自定义可重入锁
 * @author bjp-yxkj-gul
 *
 */
public class MyLock implements Lock{
	
	private boolean isLock = false;
	private Thread lockThread = null;
	private int count;
	@Override
	public synchronized void lock() {
		Thread currentThread = Thread.currentThread();
		while(isLock && currentThread !=lockThread) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isLock = true;
		count++;
		lockThread = currentThread;
		
	}
	
	@Override
	public synchronized void unlock() {
		if(lockThread == Thread.currentThread()) {
			count--;
			if(count==0) {
				isLock=false;
				notifyAll();
			}
		}
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
