package com.ai.jvm.thread;

public class ThreadInterrupted {
    public static void main(String[] args)  {
        Thread t1 = new Thread(() ->{
           while (true){
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("Hello1......");
           }
        });
        t1.start();
        Thread main = Thread.currentThread();
        Thread t2 = new Thread(()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.interrupt();
            System.out.println("interrupted...");
        });
        t2.start();
        try {
            t1.join();//join的是main线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*Thread t = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("收到打断信号");
                        e.printStackTrace();
                    }

                }
            }
        };
        t.start();
        Thread.sleep(1000);
        System.out.println(t.isInterrupted());
        t.interrupt();
        System.out.println(t.isInterrupted());*/
    }
}
