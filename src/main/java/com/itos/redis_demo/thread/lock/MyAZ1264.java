package com.itos.redis_demo.thread.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *自旋锁思想实现的2个线程交替打印
 *while()等待要消耗CUP
 */
public class MyAZ1264 {
    enum ReadyToRun{T1,T2};
    static volatile ReadyToRun r = ReadyToRun.T1;

    static AtomicInteger  ai = new AtomicInteger(1);
    static void printZM(){
        for (int i = 65; i < 65 + 26; i++) {
            while (ai.get() != 1){}
            System.out.println((char)i);
            ai.set(2);
        }
    }
    static void printSZ(){
        for (int i = 1; i <= 26; i++) {
            while (ai.get() != 2){}
            System.out.println(i);
            ai.set(1);
        }
    }
    public static void main(String[] args) {
       Thread t1 = new Thread(()->{
            for (int i = 65; i < 65 + 26; i++) {
                while (r != ReadyToRun.T1){}//如果r不等T1就一直循环等待，直到等于T1后继续执行
                System.out.println((char) i);
                r = ReadyToRun.T2;
            }
        });
        Thread t2 = new Thread(()->{
            for (int i = 1; i <= 26; i++) {
                while (r != ReadyToRun.T2){}//如果r不等T2就一直循环等待，直到等于T2后继续执行
                System.out.println(i);
                r = ReadyToRun.T1;
            }
        });
        Thread t3 = new Thread(MyAZ1264::printZM);
        Thread t4 = new Thread(MyAZ1264::printSZ);
        t3.start();
        t4.start();

    }
}
