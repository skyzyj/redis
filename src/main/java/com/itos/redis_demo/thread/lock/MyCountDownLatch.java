package com.itos.redis_demo.thread.lock;

import java.util.concurrent.CountDownLatch;

/***
 * 线程倒数计数，等待所有线程结束,每结束一个线程，计数器减1，计数器为0时，继续执行下面的代码，
 * join也可以实现相同功能
 */
public class MyCountDownLatch {
    private static void usingCountDownLatch(){
        System.out.println("usingCountDownLatch start");
        Thread[] t = new Thread[100];
        CountDownLatch latch = new CountDownLatch(t.length);
        for(int i=0;i<t.length;i++){
            t[i] = new Thread(()->{
                System.out.println("CountDownLatch-" + Thread.currentThread().getName());
                latch.countDown();
            });
        }
        for(int j=0;j<t.length;j++){
            t[j].start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("usingCountDownLatch end...");
    }
    private static void usingJoin(){
        System.out.println("usingJoin start");
        Thread[] t = new Thread[100];
        for(int i=0;i<t.length;i++){
            t[i] = new Thread(()->{
                System.out.println("Join-"+Thread.currentThread().getName());
            });
        }
        for(int j=0;j<t.length;j++){
            t[j].start();
        }

        for(int j=0;j<t.length;j++){
            try {
                t[j].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("usingJoin end");
    }
    public static void main(String[] args) {
        usingCountDownLatch();
        usingJoin();
    }
}
