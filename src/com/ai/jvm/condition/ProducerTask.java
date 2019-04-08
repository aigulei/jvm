package com.ai.jvm.condition;

import java.util.concurrent.TimeUnit;

public class ProducerTask implements Runnable {
	private Tmall tmall;
	public ProducerTask(Tmall tmall) {
		this.tmall = tmall;
	}
	@Override
	public void run() {
		while(true) {
			tmall.push();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
