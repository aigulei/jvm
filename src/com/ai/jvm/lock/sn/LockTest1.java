package com.ai.jvm.lock.sn;

/**
 * 测试重入锁
 */
public class LockTest1 {
    private BooleanLock booleanLock = new BooleanLock();

    public void a(){
        try {
            booleanLock.lock(40);
            System.out.println("A");
            b();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Lock.TimeOutException e) {
            e.printStackTrace();
        } finally {
            booleanLock.unlock();
        }
    }

    public void b(){
        try {
            booleanLock.lock(40);
            System.out.println("B");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Lock.TimeOutException e) {
            e.printStackTrace();
        } finally {
            booleanLock.unlock();
        }
    }

    public static void main(String[] args) {
        LockTest1 lockTest = new LockTest1();
        lockTest.a();
    }

}
