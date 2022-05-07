package com.itos.redis_demo.thread;

/**
 * 怎么结束线程
 * stop 方法
 */
public class ThreadStop {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            while (true){
                System.out.println("go on");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // t1.stop(); //结束线程,粗暴结束,如果有锁释放所有锁，不会处理善后
        t1.suspend();// 暂停线程,不建议用,暂停时线程持有的锁是不会被释放的
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.resume();//重新开始线程与suspend是一组
    }
}
