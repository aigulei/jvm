package com.ai.jvm.condition;

public class Target1 implements Runnable {
	private WaitAndNotify waitAndNotify;
	
	public Target1(WaitAndNotify waitAndNotify) {
		this.waitAndNotify = waitAndNotify;
	}
	
	@Override
	public void run() {
		waitAndNotify.set();
	}

}
