package com.itos.redis_demo.thread.lock;

import java.util.concurrent.locks.ReentrantLock;


public class MyReentantLock2 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);//true表示公平锁，最新要获取锁的线程先检查等待队列，
                                                            //如果队列有在它前面的等待线程，则它要等前面的线程执行完才能抢锁
        new Thread(()->{
            try {
                lock.lock();
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       Thread t2 = new Thread(()->{
            try {
                lock.lockInterruptibly();
                System.out.println("t2 ...start");

                Thread.sleep(2000);

                System.out.println("t2 ...end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(lock.isLocked())lock.unlock();
            }
        });
        t2.start();
        t2.interrupt();
    }


}
