package com.itos.redis_demo.thread;

import java.util.concurrent.locks.ReentrantLock;

public class InterruptedAndLock {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            lock.lock();
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(()->{
            //lock.lock();//抢锁的过程不会被设了打断标记影响
            try {
                lock.lockInterruptibly();//用这种方式抢锁的过程会被设置了打断标记打断
                System.out.println("t2抢到lock");
            }catch(InterruptedException e){
                System.out.println("在抢锁的过程中线程被标记打断");
            }finally {
                if(lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
            System.out.println("t2 end");
        });
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }
}
