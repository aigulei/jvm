package com.ai.jvm.lock;

public class QueueObject {
	private boolean isNotified = false;
	
	public synchronized void doWait() throws InterruptedException {
		while(!isNotified) {
			this.wait();
		}
		this.isNotified = true;
	}
	
	public synchronized void doNotify() {
		this.isNotified = true;
		this.notify();
	}
	
	@Override
	public boolean equals(Object o) {
		return this == o;
	}
}
