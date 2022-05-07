package com.itos.redis_demo.thread.lock;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyAZ1263  {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(()->{
            try {
                latch.countDown();
                lock.lock();
                for (int i = 65; i < 65 + 26; i++) {
                    System.out.println((char) i);
                    c2.signal();
                    c1.await();
                }
                c2.signal();
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(()->{
            try {
                latch.await();
                lock.lock();
                for (int i = 1; i <= 26; i++) {
                    System.out.println(i);
                    c1.signal();
                    c2.await();
                }
                c1.signal();
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();

    }
}
