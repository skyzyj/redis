package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 线程间通信
 * 实现一个容器，2个线程，1个线程往里加数据，1个线程监控容器数据个数，数量达到5个时，通知线程1
 */
public class MyCollections {
    //volatile List<Integer> list = new ArrayList();
    //Collections.synchronizedList同步容器
    //volatile static int count ;
    volatile  List<Integer> list = Collections.synchronizedList(new LinkedList<>());
    private  void add(Integer v){
        //count++;
        list.add(v);
    }
    private  int size(){
        return list.size();
    }

    public static void main(String[] args) {
        MyCollections mc =new MyCollections();

        Thread t1 = new Thread(()->{
            System.out.println("t1开始");
            for (int i=0;i<10;i++) {
                System.out.println("t1 add "+i);
                mc.add(i);
                //SleepUtils.SleepMilliseconds(1);
            }
            System.out.println("t1结束");
        });
        /*Thread t3 = new Thread(()->{
            System.out.println("t3开始");
            for (int i=0;i<10;i++) {
                System.out.println("t3 add "+i);
                mc.add(1);
                //SleepUtils.SleepMilliseconds(1);
            }
            System.out.println("t3结束");
        });*/

        Thread t2 = new Thread(()->{
            System.out.println("t2开始");
            for (;;) {
                if(mc.size() == 5 ){
                    System.out.println("容器已经有5个元素了");
                    break;
                }
            }
            System.out.println("t2结束");
        });
        t1.start();
        //t3.start();
        t2.start();
    }

}
