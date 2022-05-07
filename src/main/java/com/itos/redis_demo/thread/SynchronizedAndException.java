package com.itos.redis_demo.thread;

/**
 * 异常和锁
 * 默认情况下，发生了异常，锁是会被释放的,其他等待这个把锁的线程就有机会执行
 * 正常情况下，程序发生了异常，就会停止，继续持续会发生很难预料的后果
 */
public class SynchronizedAndException {
    int count = 0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName()+": start");
        while(true){
            count++;
            System.out.println(Thread.currentThread().getName()+": count "+count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count==5){
                int a = 10 / 0;
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedAndException t = new SynchronizedAndException();
        Runnable r = new Runnable() {//创建实现接口的匿名类
            @Override
            public void run() {
                t.m();
            }
        };
        new Thread(r).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r).start();
    }
}
