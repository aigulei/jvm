package com.ai.jvm.command;


/**
 * jps -m 传递给虚拟机的参数
 * jps -v 虚拟机参数
 * jps -l 完整路径名
 * jstat: 
 * jstat -gcutil pid 20000 20：每2秒收集进程号pid的gcutil情况，收集20次
 * jinfo:
 * jinfo -flag UseSerialGC pid:查看pid的进程号是否使用Serial的GC收集器
 * -XX:[+/-]option 设置值
 * -XX:option=value 设置值
 * @author Administrator
 *
 */
public class JvmCommand {
	public static void main(String[] args) {
		while(true) {
			System.out.println("Hello....");
			new Object();
			/*
			 * try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}
	}
}
