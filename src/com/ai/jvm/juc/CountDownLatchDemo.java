package com.ai.jvm.juc;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
	private CountDownLatch countDownLatch;
	public CountDownLatchDemo(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}
	public int getValue() {
		try {
			Random random = new Random();
			int value = random.nextInt(5000);
			try {
				Thread.sleep(Long.parseLong(value+""));
			}  catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"获取数据"+value);
			return value;
		} finally {
			countDownLatch.countDown();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(10);
		CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo(countDownLatch);
		for(int i=0;i<10;i++) {
			new Thread(() ->{
				countDownLatchDemo.getValue();
			}).start();
		}
		countDownLatch.await();
		System.out.println("执行完毕。。。。");
	}
}
