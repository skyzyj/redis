package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyCollections3 {
    volatile List<Integer> list = Collections.synchronizedList(new LinkedList<>());
    private  void add(Integer v){
        //count++;
        list.add(v);
    }
    private  int size(){
        return list.size();
    }
    public static void main(String[] args) {
        MyCollections3 c=new MyCollections3();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(()->{
            try {
                System.out.println("t2开始");
                latch.await();
                System.out.println("已经加入5个元素");
                System.out.println("t2结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            System.out.println("t1开始");
            for(int i=0;i<10;i++){
                c.add(i);
                System.out.println("add "+i);
                if (c.size() == 5){
                    latch.countDown();
                }
                SleepUtils.SleepMilliseconds(200);
            }
            System.out.println("t1结束");
        }).start();
    }
}
