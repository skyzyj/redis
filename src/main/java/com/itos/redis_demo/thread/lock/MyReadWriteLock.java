package com.itos.redis_demo.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁:共享锁和排它锁
 * 有线程读的时候加一个读锁，这时其他线程也可以读，但是写线程来了不能写，等待读线程读完之后才能写
 * 有写线程的时候加一个写锁，这时其他线程不可以读，其他写线程来了也不可以写，所有的读写必须等写线程完成之后
 */
public class MyReadWriteLock {
    static ReentrantLock reenlock = null;
    private static int value ;
    static ReadWriteLock rwlock = new ReentrantReadWriteLock();;
    static Lock rlock = null;
    static Lock wlock = null;
    static {
        reenlock = new ReentrantLock();
        rlock = rwlock.readLock();
        wlock = rwlock.writeLock();
    }
    static void read(Lock lock){
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("value="+value+" read over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    static void write(Lock lock){
        try {
            lock.lock();
            Thread.sleep(1000);
            value = value + 1;
            System.out.println("write over,value="+value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        //Runnable readR = ()->read(reenlock);
        Runnable readR = ()->read(rlock);

        //Runnable writR = ()->write(reenlock,1);
        Runnable writR = ()->write(wlock);

        for(int i=0;i<15;i++) new Thread(readR).start();
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        for (int j=0;j<2;j++) new Thread(writR).start();
    }
}
