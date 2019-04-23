package com.ai.jvm.lock.sn;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class BooleanLock implements Lock {

    private boolean initValue;

    private Collection<Thread> blockedThreadCollection = new ArrayList<>();
    private Thread lockThread;
    public BooleanLock(){
        this.initValue = false;
    }

    private int count;

    @Override
    public synchronized void lock() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        while (initValue && lockThread != currentThread){
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        blockedThreadCollection.remove(Thread.currentThread());
        this.lockThread = currentThread;
        this.initValue = true;
        this.count++;

    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if(mills <=0){
            lock();
        }
        long hasRemaing = mills;
        long endTime = System.currentTimeMillis()+mills;
        Thread currentThread = Thread.currentThread();
        while (initValue && lockThread!=currentThread){
            if(hasRemaing <=0){
                throw new TimeOutException("超时");
            }
            blockedThreadCollection.add(Thread.currentThread());
            this.wait(mills);
            hasRemaing = endTime - System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+" hasRemaing:"+hasRemaing);
        }
        this.initValue = true;
        this.lockThread = currentThread;
        this.count++;
    }

    @Override
    public synchronized void unlock() {
        if(Thread.currentThread()==this.lockThread){

            this.count--;
            if(this.count==0){
                this.initValue = false;
                Optional.of(Thread.currentThread().getName()+" release the lock monitor")
                        .ifPresent(System.out::println);
                this.notifyAll();
            }

        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockSize() {
        return blockedThreadCollection.size();
    }
}
