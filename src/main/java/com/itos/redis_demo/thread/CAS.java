package com.itos.redis_demo.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * 无锁优化,自旋锁
 * java.util.concurrent.atomic.Atomic* 都是自旋锁实现的原子类
 * * CAS是cup原语，中间不能被打断
 * ABA问题：加版本号，判断的时候，值和版本号一起判断,AtomicStampedReference专门解决ABA问题
 * LongAdder: 使用分段锁，高并发时最快，低并发没有什么优势，每个分段内部依然是使用自旋锁
 *
 */
public class CAS {
    private long count2 = 0;
    AtomicInteger count = new AtomicInteger(0);
    LongAdder count1 = new LongAdder();
    void m(){
        for(int i=0;i<10000;i++) {
            count.incrementAndGet();
        }
    }
    void m1(){
        for(int i=0;i<10000;i++) {
            count1.increment();
        }
    }
    synchronized void m2(){
        for(int i=0;i<10000;i++) {
            count2++;
        }
    }
    public static void main(String[] args) {
        CAS c = new CAS();

        List<Thread> list = new LinkedList<>();
        final long start = System.currentTimeMillis();
        for (int i=0;i<10;i++){
           list.add(new Thread(c::m2,"Thread-"+i));
        }
        list.forEach((o)->{
            o.start();

        });

        list.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(c.count2);
        System.out.println(System.currentTimeMillis()-start);
    }
}
