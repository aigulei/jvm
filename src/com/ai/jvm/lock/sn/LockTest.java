package com.ai.jvm.lock.sn;

import java.util.Optional;
import java.util.stream.Stream;

public class LockTest {

    public static void main(String[] args) throws InterruptedException {
        BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1","T2","T3","T4").forEach(name ->{
            new Thread(()->{
                try {
                    booleanLock.lock(20000);
                    Optional.of(Thread.currentThread().getName()+" have the lock monitor")
                            .ifPresent(System.out::println);
                    work();
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                } catch (Lock.TimeOutException e) {
                    e.printStackTrace();
                } finally {
                    booleanLock.unlock();
                }
            }).start();
        });
    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName()+" is working")
                .ifPresent(System.out::println);
        Thread.sleep(4_000);
    }
}
