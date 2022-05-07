package com.itos.redis_demo.thread.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏,当线程数量达到指定值时，执行一个指定的run ,如果没有达到指定的线程数量则一直等着
 */
public class MyCyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("20个线程满了，开始执行");
            }
        });

        for (int i=0;i<100;i++){
            new Thread(()->{
                try {

                    System.out.println(Thread.currentThread().getName());
                    barrier.await();//如果这个句代码放到run方法的最后，则表示先执行线程run,再执行cyclicbarrier的run
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
