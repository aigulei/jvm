package com.ai.jvm.lock.sn;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class BooleanLock implements Lock {

    private boolean initValue;

    private Collection<Thread> blockedThreadCollection = new ArrayList<>();
    private Thread currentThread;
    public BooleanLock(){
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while (initValue){
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        blockedThreadCollection.remove(Thread.currentThread());
        this.currentThread = Thread.currentThread();
        this.initValue = true;

    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if(mills <=0){
            lock();
        }
        long hasRemaing = mills;
        long endTime = System.currentTimeMillis()+mills;
        while (initValue){
            if(hasRemaing <=0){
                throw new TimeOutException("超时");
            }
            blockedThreadCollection.add(Thread.currentThread());
            this.wait(mills);
            hasRemaing = endTime - System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+" hasRemaing:"+hasRemaing);
        }
        this.initValue = true;
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if(Thread.currentThread()==this.currentThread){
            this.initValue = false;
            Optional.of(Thread.currentThread().getName()+" release the lock monitor")
                    .ifPresent(System.out::println);
            this.notifyAll();
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
