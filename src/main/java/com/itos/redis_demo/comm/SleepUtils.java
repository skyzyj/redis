package com.itos.redis_demo.comm;

import java.util.concurrent.TimeUnit;

public class SleepUtils {
    public static void SleepSecondes(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void SleepMilliseconds(int mill){
        try {
            TimeUnit.MILLISECONDS.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
