package com.ai.jvm.command;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * jstack：查看线程僵死时运行状态
 * @author Administrator
 *
 */
public class JstackExample {
	public static void main(String[] args) {
		Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
		Set<Entry<Thread, StackTraceElement[]>> entrySet = allStackTraces.entrySet();
		for(Entry<Thread, StackTraceElement[]> entry:entrySet) {
			Thread t = entry.getKey();
			System.out.println("Thread name is "+t.getName());
			StackTraceElement[] value = entry.getValue();
			for(StackTraceElement e:value) {
				System.err.println("\t" +value);
			}
		}
	}
}
