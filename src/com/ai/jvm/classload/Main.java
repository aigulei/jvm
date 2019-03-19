package com.ai.jvm.classload;

/**
 * 加载：
 * 1：通过一个类的全限定名来获取定义此类的二进制流
 * 2：将这个字节所代表的静态存储结构转化为方法区的运行时结构
 * 3：在内存中生成一个代表这个类的class对象，作为这个类的各种数据的访问入口
 * @author Administrator
 *
 */
public class Main {
	public static void main(String[] args) {
		Parent.hello();
	}
}
