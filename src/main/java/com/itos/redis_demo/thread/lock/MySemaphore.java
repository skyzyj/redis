package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.concurrent.Semaphore;

/**
 * 信号灯
 * 限流，最多允许多少个线程同时执行
 *
 */
public class MySemaphore {
    public static void main(String[] args) {
        //Semaphore semaphore = new Semaphore(2);//允许permits个线程同时执行
        Semaphore semaphore = new Semaphore(2,false);//允许permits个线程同时执行,第二个参数是否公平锁
        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T1 runing");
                SleepUtils.SleepMilliseconds(200);
                System.out.println("T1 runing");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }).start();

        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T2 runing");
                SleepUtils.SleepMilliseconds(200);
                System.out.println("T2 runing");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }).start();

        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T3 runing");
                SleepUtils.SleepMilliseconds(200);
                System.out.println("T3 runing");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }).start();
        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T4 runing");
                SleepUtils.SleepMilliseconds(200);
                System.out.println("T4 runing");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }).start();


    }
}
