package com.ai.jvm.condition.cn;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyDataSource {
	
	private static final int MAX_SIZE = 10;
	
	private static final LinkedList<Integer> POOL = new LinkedList<Integer>();
	
	public MyDataSource() {
		for(int i =0;i<MAX_SIZE;i++) {
			POOL.add(i);
		}
	}
	
	public int getResult() {
		synchronized(POOL) {
			int i = 0;
			while(POOL.size()==0) {
				System.out.println("连接池空，无法获取连接，"+Thread.currentThread().getName()+"进入等待！！！");
				try {
					POOL.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			i = POOL.removeFirst();
			System.out.println("获取连接:"+i);
			POOL.notifyAll();
			return i;
		}
		
	}
	
	public void putResult(int i) {
		synchronized (POOL) {
			while(POOL.size()==MAX_SIZE) {
				System.out.println("连接池满，无法放入数据,"+Thread.currentThread().getName()+"进入等待！！！");
				try {
					POOL.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			POOL.add(i);
			POOL.notifyAll();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread();
		thread.join();
//		MyDataSource dataSource = new MyDataSource();
//		Random random = new Random();
//		for(int i =0;i<20;i++) {
//			new Thread(() -> {
//				int result = dataSource.getResult();
//				try {
//					TimeUnit.SECONDS.sleep(random.nextInt(5)+1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				dataSource.putResult(result);
//			} ).start();
//		}
		
	}
}
