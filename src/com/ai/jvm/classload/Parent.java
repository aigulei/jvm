package com.ai.jvm.classload;

/**
 * 初始化:
 * 1:遇到new、getstatic、putstatic 或invokestatic这4条字节码指令时，
 * 如果累没有进行初始化，则需要先触发其初始化。生成这4条指令的最常见的java代码场景是：
 * 使用new关键字实例化对象的时候、读取或设置一个类的静态字段（被final修饰、已在编译器把
 * 结果放入常量池的静态字段除外）的时候，以及调用一个类的静态方法的时候
 * 2：使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没有进行过初始化，
 * 则需要先触发其初始化
 * 3：当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化
 * 4：当虚拟机启动时，用户需要指定一个要执行的主类，虚拟机会先初始化这个主类
 * 不被初始化的例子：
 * 1：通过子类引用父类的静态字段
 * 2:通过数组定义来引用类
 * 3：引用类的常量
 * @author Administrator
 * new,直接使用
 * 访问某个类或者接口的静态变量的时候，或者对该静态变量进行赋值操作
 * 调用静态方法
 * 反射某个类
 * 初始化一个子类
 * 启动类
 *
 */
public class Parent {
	static {
		System.out.println("Parent 初始化了。。。");
	}
	public static int num = 10;
	public static void hello() {
		System.out.println("Parent Hello");
	}
}
