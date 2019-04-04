package com.ai.jvm.lock.aqs;

public class MyAqsLockTest {
	MyAqsLock myAqsLock = new MyAqsLock();
	private int value;
	
	public int getValue() {
		try {
			myAqsLock.lock();
			value++;
		} finally {
			myAqsLock.unlock();
		}
		return value;
	}
	
	public void a() {
		myAqsLock.lock();
		System.out.println("A");
		b();
		myAqsLock.unlock();
	}
	
	public void b() {
		myAqsLock.lock();
		System.out.println("B");
		myAqsLock.unlock();
	}
	
	public static void main(String[] args) {
		MyAqsLockTest myAqsLockTest = new MyAqsLockTest();
		myAqsLockTest.a();
//		new Thread(()->{
//			while(true) {
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println(Thread.currentThread()+".."+
//						myAqsLockTest.getValue());
//			}
//			
//		}).start();
//		
//		new Thread(()->{
//			while(true) {
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println(Thread.currentThread()+".."+
//						myAqsLockTest.getValue());
//			}
//		}).start();
//		
//		new Thread(()->{
//			while(true) {
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println(Thread.currentThread()+".."+
//						myAqsLockTest.getValue());
//			}
//		}).start();
	}
}
