package com.ai.jvm;

/**
 * 	对象优先分配到Eden
 * 	大对象直接分配到老年代
 * 	长期存活的对象分配到老年代
 * 	空间分配担保
 * 	动态对象年龄判断
 * 	打印GC日志参数
 * -verbose:gc -verbose:class -XX:+PrintGCDetails
 * -XX:+UseSerialGC
 * -Xms20M 初始内存大小
 * -Xmx20M 内存上限
 * -XX:SurvivoRatio=8
 * -Xmn20M
 * -XX:PretenureSizeThreshold 大对象直接进入老年代
 * -XX:MaxTenuringThreshold 长期存活的对象将进入到老年代
 * -XX:+HandlePromotionFailure 空间分配担保
 * 
 * @author Administrator
 */
public class PrintGCDetail {
	public static void main(String[] args) {
		System.out.println("Hello,World");
		byte[] b1 = new byte[8*1024*1024];
		byte[] b2 = new byte[8*1024*1024];
		byte[] b3 = new byte[8*1024*1024];
		byte[] b4 = new byte[8*1024*1024];
		byte[] b5 = new byte[8*1024*1024];
	}
}
