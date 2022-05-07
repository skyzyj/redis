package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.concurrent.CountDownLatch;

/**
 * 使用synchronized,wait,notify实现A1B2...Z26，2个线程交替打印
 */
public class MyAZ1262 {
    public static void main(String[] args) {
        final Object lock = new Object();
        CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                latch.countDown();
                for (int i = 65; i < 65 + 26; i++) {
                    System.out.println((char) i);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    latch.await(); //这句代码在哪个线程中，如果latch的count不为0，就阻塞哪个线程且释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 1; i <= 26; i++) {
                    System.out.println(i);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
       // SleepUtils.SleepMilliseconds(100);
        t2.start();
    }
}
