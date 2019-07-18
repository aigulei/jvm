package com.ai.jvm.tl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
	private static final ThreadPoolExecutor executor = 
			new ThreadPoolExecutor(5, 10, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10) );
	public static void main(String[] args) throws InterruptedException {
		
		Collection<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		for(int i=0;i<10;i++) {
			tasks.add(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					System.out.println(Thread.currentThread().getName()+"...start");
					Thread.sleep(10000);
					System.out.println(Thread.currentThread().getName()+"...end");

					return null;
				}
			});
		}
		executor.invokeAll(tasks);
		System.out.println("开始下一步了ok了。。。。");
		executor.shutdown();
	}
}
