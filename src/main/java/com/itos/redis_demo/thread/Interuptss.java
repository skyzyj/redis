package com.itos.redis_demo.thread;

import java.util.concurrent.TimeUnit;

public class Interuptss {
    public static void main(String[] args){
        Thread t1 =new Thread(()->{
            for (;;){
                if (Thread.interrupted()){
                    System.out.println("Thread is interrupted");
                    System.out.println(Thread.currentThread().getName()+"  "+Thread.interrupted());
                }
            }
        },"t1");
        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        System.out.println(Thread.currentThread().getName()+" t "+Thread.interrupted());
    }
}
