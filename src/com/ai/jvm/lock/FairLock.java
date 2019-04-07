package com.ai.jvm.lock;

import java.util.ArrayList;
import java.util.List;

/**
 * 公平锁
 * @author bjp-yxkj-gul
 *
 */
public class FairLock {
	private boolean isLocked = false;
	private Thread lockingThread = null;
	private List<QueueObject> waitThreads = new ArrayList<>();
	
	public void lock() throws InterruptedException {
		QueueObject queueObject = new QueueObject();
		synchronized (this) {
			waitThreads.add(queueObject);
		}
		
		try {
			queueObject.doWait();
		} catch (InterruptedException e) {
			synchronized(this) {
				waitThreads.remove(queueObject);
			}
			throw e;
		}
	}
	
	public synchronized void unlock() {
		if(this.lockingThread != Thread.currentThread()) {
			throw new IllegalMonitorStateException("Calling thread has not locked this lock");
		}
		isLocked = false;
		lockingThread = null;
		if(waitThreads.size()>0) {
			waitThreads.get(0).doNotify();
		}
	}
	
}
