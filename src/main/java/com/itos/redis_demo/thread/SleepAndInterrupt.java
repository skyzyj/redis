package com.itos.redis_demo.thread;

public class SleepAndInterrupt {
    public static void main(String[] args) {
        Object o = new Object();
        Thread t1 = new Thread(()->{
            synchronized (o){
                try {
                    //Thread.sleep(3000);

                    o.wait();
                } catch (InterruptedException e) {
                    System.out.println("在线程中sleep时，且线程被标记打断，该触发该异常");
                    System.out.println(Thread.currentThread().isInterrupted());
                }
            }
        },"t1");
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }
}
