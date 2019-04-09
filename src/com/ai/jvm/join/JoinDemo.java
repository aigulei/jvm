package com.ai.jvm.join;

public class JoinDemo {
	public void a(Thread joinThread) {
		System.out.println("方法A执行了");
		joinThread.start();
		try {
			joinThread.join();
			joinThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("方法A执行完毕");
	}
	
	public void b() {
		System.out.println("加塞线程开始执行");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("加塞线程执行完毕");
	}
	public static void main(String[] args) {
		JoinDemo joinDemo = new JoinDemo();
		Thread joinThread = new Thread(()->{
			joinDemo.b();
		}); 
		joinDemo.a(joinThread);
	}
}
