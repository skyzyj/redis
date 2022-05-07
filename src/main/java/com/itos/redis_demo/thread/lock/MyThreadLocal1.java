package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 申明式事务用到,一个线程的事务操作必须是由一个数据库连接完成
 */
public class MyThreadLocal1 {
    static volatile ThreadLocal<Person> person = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(()->{
            SleepUtils.SleepSecondes(2);
            System.out.println(person.get());//ThreadLocal，get的是当前线程Map里面的person

        }).start();
        new Thread(()->{
            // SleepUtils.SleepSecondes(1);
            person.set(new Person());//ThreadLocal.set到当前线程的Map里面，其他线程看不见
        }).start();
    }

    static class Person{
        String name = "zhangsan";

        @Override
        public String toString() {
            return "Person[name="+name+"]";
        }
    }
}
