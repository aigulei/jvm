package com.ai.jvm.condition;

public class TmallTest {
	public static void main(String[] args) {
		Tmall tmall = new Tmall();
		ConsumerTask consumerTask = new ConsumerTask(tmall);
		ProducerTask producerTask = new ProducerTask(tmall);
		
		new Thread(producerTask).start();
		new Thread(producerTask).start();
		new Thread(producerTask).start();
		
		new Thread(consumerTask).start();
		new Thread(consumerTask).start();
		new Thread(consumerTask).start();
		new Thread(consumerTask).start();
		new Thread(consumerTask).start();
	}
}
