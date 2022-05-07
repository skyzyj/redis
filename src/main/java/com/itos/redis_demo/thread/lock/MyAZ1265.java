package com.itos.redis_demo.thread.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class MyAZ1265 {
    public static void main(String[] args) {
        BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
        BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(()->{
            latch.countDown();
            for (int i = 65; i < 65+26; i++) {
                System.out.println((char)i);
                try {
                    q1.put("ok");
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()->{
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= 26; i++) {
                try {
                    System.out.println(i);
                    q1.take();
                    q2.put("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
