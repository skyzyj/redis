package com.itos.redis_demo.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 乱序验证
 */
public class Disorder {
    private static int x=0,y=0;
    private static int a=0,b=0;

    public static void main(String[] args) {
        for(long i=0;i<Long.MAX_VALUE;i++) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch latch = new CountDownLatch(2);
            Thread one = new Thread(()->{
                a = 1;
                x = b;
                latch.countDown();
            });
            Thread two = new Thread(()->{
                b = 1;
                y = a;
                latch.countDown();
            });

            one.start();
            two.start();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = "第" + i +"次("+ x+","+y+")";
            if(x==0 && y==0){
                System.out.println(result);
                break;
            }
        }
    }
}
