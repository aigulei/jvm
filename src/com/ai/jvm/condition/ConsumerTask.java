package com.ai.jvm.condition;

import java.util.concurrent.TimeUnit;

public class ConsumerTask implements Runnable{
	private Tmall tmall;
	public ConsumerTask(Tmall tmall) {
		this.tmall = tmall;
	}
	@Override
	public void run() {
		while(true) {
			tmall.take();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
