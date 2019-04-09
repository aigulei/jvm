package com.ai.jvm.condition.wn;

public class Target2 implements Runnable {
	private WaitAndNotify waitAndNotify;
	
	public Target2(WaitAndNotify waitAndNotify) {
		this.waitAndNotify = waitAndNotify;
	}
	
	@Override
	public void run() {
		waitAndNotify.get();
	}

}
