package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.concurrent.locks.LockSupport;

/**
 * 2个线程 交叉打印A1B2C3...Z26
 */
public class MyAZ126 {
    static Thread t1,t2;
    static boolean ist1strt = false;
    private static void printZM(){
        ist1strt = true;
        for (int i = 65; i < 65+26; i++) {
            System.out.println((char)i);
            LockSupport.unpark(t2);
            LockSupport.park();
        }
        LockSupport.unpark(t2);
    }
    private static void printSZ(){
        if (!ist1strt){
            return;
        }
        for (int i = 1; i <= 26; i++) {
            System.out.println(i);
            LockSupport.unpark(t1);
            LockSupport.park();
        }
        LockSupport.unpark(t1);
    }
    public static void main(String[] args) {
        MyAZ126 myAZ126 = new MyAZ126();
        t1 = new Thread(MyAZ126::printZM);
        SleepUtils.SleepMilliseconds(100);
        t2 = new Thread(MyAZ126::printSZ);
        t1.start();
        t2.start();
    }
}
