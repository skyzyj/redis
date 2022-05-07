package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyProducterConsumer1<T> {
    private final LinkedList<T> lists = new LinkedList();//容器
    private final int MAX  = 10;//缓冲区最大的容量
    private volatile int count=0;//缓冲区中对象T的个数
    private volatile int index;//生产者生产对象流水好号
    private volatile int pi = 0;
    private volatile int ci = 0;

    private ReentrantLock lock; //定义同步可重入锁，无论是生产者还是消费者线程，都使用这把锁，且同时只有一个
                                 //线程（消费者或生产者）能够申请到这把锁
    private Condition producer;
    private Condition consumer;

    public MyProducterConsumer1(){
        lock = new ReentrantLock(); //ReentrantLock可以使用lock.newCondition()定义多个等待队列
        producer = lock.newCondition();//定义锁对象的一个等待队列，synchronized 只有一个等待队列
        consumer = lock.newCondition();//定义锁对象的一个等待队列,synchronized 只有一个等待队列
    }

    public void put(T t){//多线程同步生产对象T
        try{
            lock.lock();
            while (lists.size() == MAX){
                producer.await(); //当前线程进入producer等待队列等待
            }
            lists.add(t);
            ++count;
            ++index;
            consumer.signalAll();//叫醒consumer等待队列的所有线程
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public  T get(){//多线程同步消费对象T
        T t = null;
        try {
            lock.lock();
            while (lists.size() == 0) {//不能用if,被叫醒的线程必须继续判断，因为多线程下，
                                        //当前对象叫醒后,可能他前面有线程消费了最后一个t,继续执行就有空指针异常
                consumer.await();//当前线程进入consumer等待队列进行等待
            }
            t = lists.removeFirst();
            count--;
            producer.signalAll(); //叫醒producer等待队列的所有线程,但是不释放锁，和notify一样
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {
        MyProducterConsumer1<ManTou> mpc = new MyProducterConsumer1<>();

        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                final int x = ++mpc.pi;
                for (int j = 0; j < 25; j++) {
                    ManTou manTou = new ManTou("生产者:"+x+"号馒头: "+(mpc.index+1)+"号");
                    mpc.put(manTou);
                    System.out.println("生产者"+x+"号生产"+mpc.index+"号馒头");
                }
            }).start();
        }
        //SleepUtils.SleepMilliseconds(1000);

        for (int i = 0; i < 10 ; i++) {
            new Thread(()->{
                final int y = ++mpc.ci;
                for (int j = 0; j < 5; j++) {
                    System.out.println("消费者:"+y+"号消费 "+mpc.get());
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
            return this.id;
        }
    }
}
