package com.ai.jvm.lock;

import java.util.concurrent.locks.Lock;

public class Demo {
	private Lock myLock = new MyLock();
	
	public void a() {
		myLock.lock();
		System.out.println("a");
		b();
		myLock.unlock();
	}
	public void b() {
		myLock.lock();
		System.out.println("b");
		c();
		myLock.unlock();
	}
	public void c() {
		myLock.lock();
		System.out.println("c");
		myLock.unlock();
	}
	public static void main(String[] args) {
		Demo demo  = new Demo();
		demo.a();
	}
}
