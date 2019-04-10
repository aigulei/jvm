package com.ai.jvm.juc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	Random random = new Random();
	Long value = Long.valueOf(random.nextInt(5000));
	
	public void metting(CyclicBarrier cyclicBarrier) {
		try {
			Thread.sleep(Long.valueOf(random.nextInt(5000)));
			System.out.println(Thread.currentThread().getName()+"已经到达，等待开会");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	public void saying(CyclicBarrier cyclicBarrier) {
		try {
			Thread.sleep(Long.valueOf(random.nextInt(5000)));
			System.out.println(Thread.currentThread().getName()+"已经准备好，等待发言");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(10,() -> {
			System.out.println("线程名...."+Thread.currentThread().getName());
			System.out.println("执行下一步骤");
			countDownLatch.countDown();
		})  ;
		CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo();
		for(int i=0;i<10;i++) {
			new Thread(()->{
				cyclicBarrierDemo.metting(cyclicBarrier);
			}).start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//cyclicBarrier.reset();
		for(int i=0;i<10;i++) {
			new Thread(()->{
				cyclicBarrierDemo.saying(cyclicBarrier);
			}).start();
		}
	}
}
