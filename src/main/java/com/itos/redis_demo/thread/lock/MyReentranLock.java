package com.itos.redis_demo.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可以重入锁，可代替synchronized,底层是使用CAS实现的
 * ReentrantLock和synchronied差不多，但是必须要手动释放锁，synchronied当遇到异常jvm会自动释放锁
 *ReentrantLock可以尝试锁定(trylock)，可以决定是不是需要等待，或者自定义等待时间,不管是否锁定程序继续执行
 *
 * ReentrantLock有先来后到的公平锁机制，在构成函数中传入true，默认是非公平锁，synchronized没有公平锁
 */
public class MyReentranLock {
    ReentrantLock lock = new ReentrantLock();

    void m1(){
            try {
                lock.lock();
                for(int i=0;i<20;i++){
                    Thread.sleep(200);
                    System.out.println(i);
                    if(i==3){
                        m2();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();//必须要解锁
            }

    }
    void m2(){
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" m2...");
        }finally {
            lock.unlock();
        }
    }
    //可以trylock尝试锁定，如果在规定的时间内没有拿到锁，则锁定失败，且程序继续执行
    void m3(){
        boolean locked = false;
        try {
            locked = lock.tryLock(1, TimeUnit.SECONDS);//尝试获取锁，不管是否获取到程序继续向下走
            System.out.println("m3...,是否锁定: "+locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(locked) lock.unlock();
        }

    }

    public static void main(String[] args) {
        MyReentranLock  mrlock = new MyReentranLock();
        new Thread(mrlock::m1).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(mrlock::m3).start();

    }
}
