package com.ai.jvm.lock.sn;

public class SynchronizedProblem {

    public static void main(String[] args) throws InterruptedException {
        new Thread(){
            @Override
            public void run() {
                SynchronizedProblem.run();
            }
        }.start();
        Thread.sleep(1000);

        Thread t2 = new Thread(){
            @Override
            public void run() {
                SynchronizedProblem.run();
            }
        };
        t2.start();
        Thread.sleep(2000);
        t2.interrupt();
        System.out.println(t2.isInterrupted());
    }

    private synchronized  static void run(){
        System.out.println(Thread.currentThread().getName());
        while (true){

        }
    }
}
