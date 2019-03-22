package com.ai.jvm.classload;

/**
 * 加载：
 * 1：通过一个类的全限定名来获取定义此类的二进制流
 * 2：将这个字节所代表的静态存储结构转化为方法区的运行时结构
 * 3：在内存中生成一个代表这个类的class对象，作为这个类的各种数据的访问入口
 * 验证：
 * 1：验证是连接的地一步，这一阶段的目的是为了确保确保class文件的字节流中包含的信息
 * 符合当前虚拟机的要求，并且不会危害虚拟机自身的安全
 * 2：文件格式验证
 * 3：元数据验证
 * 4：字节码验证
 * 5：符号引用验证
 * 准备：
 * 1：准备阶段正式为类变量分配内存并设置变量的初始值，这些变量使用的内存都将在方法区中进行分配
 * 2：这里的初始值并非我们指定的值，而是其默认值。但是如果被final修饰，那么这个过程中，常量会
 * 被一同指定
 * 3：int 0,Boolean false ,Float 0.0 ,char '0',抽象数据类型 null 
 * @author Administrator
 * 解析：
 * 1：解析阶段是虚拟机将常量池中的符号引用替换为直接引用的过程
 * 2：类或接口的解析
 * 3：字段解析
 * 4：类方法解析
 * 5：接口方法解析
 * 初始化：
 * 1：初始化是类加载的最后一步
 * 2：初始化是执行clinit方法的过程
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		MyClassLoader classLoader = new MyClassLoader();
		Object c = classLoader.loadClass("com.ai.jvm.classload.MyClassLoader").newInstance();
		System.out.println(c.getClass());
		System.out.println(c instanceof MyClassLoader);
	}
	/*
	 * public static void main(String[] args) { Parent.hello(); }
	 */
}
