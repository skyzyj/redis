package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.concurrent.locks.LockSupport;

/**
 *锁的支持
 * 可以让线程阻塞，继续运行
 */
public class MyLockSupport {

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                SleepUtils.SleepMilliseconds(200);
                if(i==5) {
                    LockSupport.park(); // 线程阻塞
                }
                if(i==7) {
                    LockSupport.park(); // 线程阻塞
                }
                System.out.println(i);
            }
        },"T1");
        t1.start();
        //SleepUtils.SleepSecondes(4);

        LockSupport.unpark(t1);//解除线程阻塞
        System.out.println("解除线程T1阻塞");
        //SleepUtils.SleepSecondes(2);//不休眠2次unpark其实只是解除了1次park
        LockSupport.unpark(t1);
    }
}
