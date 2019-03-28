package com.ai.jvm.lock;

public class Sequence {
	private static int value;
	
	/**
	 * 	放在普通方法上，内置锁就是当前类的实例
	 * @return
	 */
	public synchronized int getSequence() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return value++;
	}
	
	/**
	 * 	放在静态方法上，内置锁就是当前的class字节度下行
	 * @return
	 */
	public synchronized static int getPrevious() {
		return value--;
	}
	
	public int getAbc() {
		synchronized (this) {
			value++;
		}
		return value;
	}
	public static void main(String[] args) {
		Sequence sequence = new Sequence();
		new Thread(()->{
			while(true) {
				System.out.println(Thread.currentThread().getName()+ "..."+sequence.getSequence());
			}
			
		}).start();
		new Thread(()-> {
			while(true) {
				System.out.println(Thread.currentThread().getName()+ "..."+sequence.getSequence());
			}
		}).start();
	}
}
