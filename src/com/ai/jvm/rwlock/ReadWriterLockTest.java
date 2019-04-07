package com.ai.jvm.rwlock;

public class ReadWriterLockTest {
	public static void main(String[] args) {
		ReadWriterLockDemo demo = new ReadWriterLockDemo();
		
		demo.putValue("key1", "hello");
		/*new Thread(()->{
			demo.putValue("key1","value1");
		}).start(); */
		
		new Thread(()->{
			
			System.out.println(demo.getValue("key1"));
		}).start();
		
		new Thread(()->{
			System.out.println(demo.getValue("key1"));
		}).start();
		
		new Thread(()->{
			System.out.println(demo.getValue("key1"));
		}).start();
		
		/*new Thread(()->{
			demo.putValue("key3","value3");
		}).start();
		
		new Thread(()->{
			demo.putValue("key4","value4");
		}).start();*/
	}
}
