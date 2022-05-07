package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class MyCollections4 {
    volatile List<Integer> list = Collections.synchronizedList(new LinkedList<>());
    private  void add(Integer v){
        list.add(v);
    }
    private  int size(){
        return list.size();
    }
    static Thread t1,t2;

    public static void main(String[] args) {
        MyCollections4 c = new MyCollections4();
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2 开始");
                if (c.size() != 5) {
                    System.out.println("t2 阻塞中");
                    LockSupport.park();
                }
                System.out.println("已经添加5个元素");
                System.out.println("t2 结束");
                LockSupport.unpark(t1);
            }
        });

        t1 = new Thread(()->{
            System.out.println("t1 开始");
            for (int i=0;i<10;i++){
                c.add(i);
                System.out.println("add "+i);
                if(c.size()==5){
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        });

        t2.start();
        SleepUtils.SleepMilliseconds(10);
        t1.start();
    }
}
