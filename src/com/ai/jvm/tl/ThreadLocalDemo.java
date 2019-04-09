package com.ai.jvm.tl;

public class ThreadLocalDemo {
	private ThreadLocal<Integer> count = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return new Integer(0);
		};
	};
	
	public int getNext() {
		Integer value = count.get();
		value++;
		count.set(value);
		return value;
	}
	
	public static void main(String[] args) {
		ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
		new Thread(() ->{
			while(true) {
				System.out.println(Thread.currentThread().getName()+" "+threadLocalDemo.getNext());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} ) .start();
		
//		new Thread(() ->{
//			while(true) {
//				System.out.println(Thread.currentThread().getName()+" "+threadLocalDemo.getNext());
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		} ) .start();
//		
//		new Thread(() ->{
//			while(true) {
//				System.out.println(Thread.currentThread().getName()+" "+threadLocalDemo.getNext());
//				try {
//					Thread.sleep(800);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		} ) .start();
	}
}
