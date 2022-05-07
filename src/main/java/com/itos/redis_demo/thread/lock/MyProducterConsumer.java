package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;

/**
 * 支持2个生成者10个消费者的生产者消费者模型
 */
public class MyProducterConsumer<T> { // 缓冲区类
    private final LinkedList<T> lists = new LinkedList();//容器
    private final int MAX  = 10;//缓冲区最大的容量
    private volatile int count=0;//缓冲区中对象T的个数
    private volatile int index;//生产者一个生产了多少个T对象

   public synchronized void put(T t){//多线程同步生产对象T
       while (lists.size()==MAX){ //不能用if，因为当前线程被叫醒后需要继续去判断，而不是叫醒后直接往下执行
           try {                   //不判断可能当前容器已经装了MAX个t,直接执行下去可能让容器装MAX+1个t
               this.wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
       lists.add(t);
       ++count;
       ++index;
       this.notifyAll();
   }
   public synchronized T get(){//多线程同步消费对象T
       T t = null;
       while (lists.size() == 0){//不能用if,被叫醒的线程必须继续判断，因为多线程下，
           try {                  //当前对象叫醒后可能他前面有线程消费了最后一个t,继续执行就有空指针异常
               this.wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
       t=lists.removeFirst();
       count--;
       this.notifyAll();
       return t;
   }

    public static void main(String[] args) {
        MyProducterConsumer<ManTou> mpc = new MyProducterConsumer<>();

        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    ManTou manTou = new ManTou((mpc.index+1)+"号馒头");
                    mpc.put(manTou);
                    System.out.println("生产"+mpc.index+"号馒头");
                }
            }).start();
        }
        SleepUtils.SleepMilliseconds(200);

        for (int i = 0; i < 10 ; i++) {

            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    System.out.println("消费者号消费"+mpc.get());
                }
            }).start();
        }
    }

    static class ManTou{
        String id;
        public ManTou(String  id){
            this.id = id;
        }

        @Override
        public String toString() {
            return "馒头"+this.id;
        }
    }

}
