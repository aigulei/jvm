package com.ai.jvm.classload;

public class Child extends Parent {
	static {
		System.out.println("Child 初始化了。。。");
	}
	public static void hello() {
		System.out.println("Child Hello");
	}
	public static void main(String[] args) {
	}
}
