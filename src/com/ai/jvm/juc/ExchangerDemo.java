package com.ai.jvm.juc;

import java.util.concurrent.Exchanger;

/**
 * exchanger放在哪个方法里面无所谓，
 * 只需要放1个就可以了，如果2个都放的话，那么2个都会执行，所以只需要执行一次就好了
 * @author Administrator
 *
 */
public class ExchangerDemo {
	private Exchanger<String> exchanger;
	
	public ExchangerDemo(Exchanger<String> exchanger) {
		this.exchanger = exchanger;
	}
	
	public void a() {
		System.out.println("执行方法A");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String res1 = "123456";
		try {
			System.out.println("等待对比....");
			String value = exchanger.exchange(res1);
//			System.out.println("a....value..."+value);
//			System.out.println("a...开始进行对比");
//			System.out.println("a比对结构"+res1.equals(value));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void b() {
		System.out.println("执行方法B");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String res = "12345";
		try {
			String value = exchanger.exchange(res);
			System.out.println("b.....value....."+value);
			System.out.println("b...开始进行对比");
			System.out.println("b比对结构"+value.equals(res));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Exchanger<String> exchanger = new Exchanger<String>();
		ExchangerDemo exchangerDemo = new ExchangerDemo(exchanger);
		
		new Thread(() -> {
			exchangerDemo.a();
		} ).start();
		
		new Thread(() -> {
			exchangerDemo.b();
		} ).start();
	}
}
