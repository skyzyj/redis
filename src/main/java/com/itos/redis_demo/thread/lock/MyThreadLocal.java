package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

/**
 *
 *
 */
public class MyThreadLocal {
    volatile static Person person = new Person();
    public static void main(String[] args) {
        new Thread(()->{
            SleepUtils.SleepSecondes(2);
            System.out.println(person.name);

        }).start();
        new Thread(()->{
           // SleepUtils.SleepSecondes(1);
            person.name = "lisi";
        }).start();
    }
    static class Person{
        String name = "zhangsan";
    }
}
