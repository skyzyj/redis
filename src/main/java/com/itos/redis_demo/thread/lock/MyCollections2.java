package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyCollections2 {
    volatile List<Integer> list = Collections.synchronizedList(new LinkedList<>());
    private  void add(Integer v){
        list.add(v);
    }
    private  int size(){
        return list.size();
    }

    public static void main(String[] args) {
        MyCollections2 c = new MyCollections2();
        final Object lock = new Object();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 开始");
                synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        c.add(i);
                        System.out.println("t1 add " + i);
                        if (c.size() == 5) {
                            try {
                                lock.notifyAll();//notify不释放锁,只唤醒拥有当前锁的等待线程
                                lock.wait();//释放锁，当前拥有锁的线程进入等待状态
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        SleepUtils.SleepMilliseconds(200);
                    }
                }
                System.out.println("t1 结束");
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2 开始");
                synchronized (lock) {
                    if (c.size() != 5) {
                        try {
                            lock.wait();//是当前线程进入等待队列等待执行
                            System.out.println("t2等待中...");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("容器已经添加5个元素");
                    lock.notify();//是拥有当前锁的所有线程如果进入了等待队列，全部唤醒
                }
                System.out.println("t2 结束");
            }
        },"t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t3 开始");
                synchronized (lock) {
                    if (c.size() != 5) {
                        try {
                            lock.wait();
                            System.out.println("t3等待中...");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("容器已经添加5个元素");
                    lock.notify();
                }
                System.out.println("t3 结束");
            }
        },"t3");

        t2.start();
        t3.start();
        SleepUtils.SleepMilliseconds(1000);
        t1.start();
    }
}
