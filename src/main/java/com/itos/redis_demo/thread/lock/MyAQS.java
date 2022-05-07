package com.itos.redis_demo.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *同步的核心是AQS类
 */
public class MyAQS {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        new Thread(()->{
            lock.lock();
            System.out.println("hello");
            lock.unlock();
        }).start();
    }
}
