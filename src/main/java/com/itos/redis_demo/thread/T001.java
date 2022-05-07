package com.itos.redis_demo.thread;

import sun.misc.Contended;

import java.util.concurrent.CountDownLatch;

public class T001 {
    public static long COUNT = 100000000;

    private static class T{
        //private long p1,p2,p3,p4,p5,p6,p7;
        @Contended //保证数据不会在一个缓存行,此参数需要去掉-XX:-RestrictContended，只有1.8起作用
        public long x = 0l;
        //private long p8,p9,p10,p11,p12,p13,p14;
    }
    public static T[] orr= new T[2];
    static {
        orr[0] = new T();
        orr[1] = new T();
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(()->{
            for(long i=0;i<COUNT;i++){
                orr[0].x = i;
            }
            latch.countDown();
        });

        Thread t2 = new Thread(()->{
            for(long j=0;j<COUNT;j++){
                orr[1].x = j;
            }
            latch.countDown();
        });
        final long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
