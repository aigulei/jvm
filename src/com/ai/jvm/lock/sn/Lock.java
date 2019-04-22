package com.ai.jvm.lock.sn;

import java.util.Collection;
import java.util.Collections;

public interface Lock {

    class TimeOutException extends  Exception{
        public TimeOutException(String message){
            super(message);
        }
    }

    void lock() throws  InterruptedException;

    void lock(long mills)throws  InterruptedException,TimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockSize();
}
