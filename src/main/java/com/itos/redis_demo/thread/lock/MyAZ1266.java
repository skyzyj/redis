package com.itos.redis_demo.thread.lock;

import java.util.concurrent.Exchanger;

/**
 * 无法实习的
 */
public class MyAZ1266 {
    private static  Exchanger<String> exchanger = new Exchanger<>();
    public static void main(String[] args) {
        new Thread(()->{

            for (int i = 65; i < 65+26; i++) {
                System.out.println((char)i);
                try {
                    exchanger.exchange("T1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()->{
            for (int i = 1; i <= 26; i++) {
                try {
                    exchanger.exchange("T2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }).start();
    }
}
