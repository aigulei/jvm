package com.ai.jvm.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 锁降级
 * 锁降级是指写锁降级为读锁
 * 在写锁没有释放的时候，获取到读锁，再释放写锁
 * 锁升级
 * 锁升级是指读锁升级为写锁
 * 在读锁没有释放的时候，获取到写锁，再释放读锁
 * 注意：ReadWriterLock不支持锁升级
 * @author bjp-yxkj-gul
 *
 */
public class ReadWriterLockDemo {
	
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	private ReadLock readLock = rwLock.readLock();
	private WriteLock writeLock = rwLock.writeLock();
	
	private final Map<String,Object> map = new HashMap<>();
	
	public Object getValue(String key) {
		readLock.lock();
		System.out.println(Thread.currentThread().getName()+"读操作在执行");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Object o = map.get(key);
		readLock.unlock();
		System.out.println(Thread.currentThread().getName()+"读操作执行完毕");

		return o;
	}
	
	public void putValue(String key,Object o) {
		writeLock.lock();
		System.out.println(Thread.currentThread().getName()+"写操作在执行");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		map.put(key, o);
		writeLock.unlock();
		System.out.println(Thread.currentThread().getName()+"写操作执行完毕");

	}
	
	

}
