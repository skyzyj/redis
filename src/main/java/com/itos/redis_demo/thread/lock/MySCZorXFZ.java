package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 生产者消费者:多线程协作模型，一部分线程生成，一部分线程消费
 * 为了更好的协作，需要建立一个缓冲区，生成的对象放入缓冲区，消费者从缓冲区拿生成的对象
 * 需要注意的是：如果缓冲区没有对象了，要锁住缓冲区，不让消费者拿对象，生产者生产对象
 *              如果缓冲区满了，要锁住缓冲区，不让生产者再继续生产，消费者消费对象
 * 4大组件：数据，缓冲区，生产者，消费者
 */
public class MySCZorXFZ {
    //数据
    class ManTou{
        String id;
        public ManTou(String  id){
            this.id = id;
        }

        @Override
        public String toString() {
            return "馒头"+this.id;
        }
    }
    //缓冲区(具备往缓冲区加数据，和取数据的功能)
    class SyncStack<T>{
        private Integer maxCount;
        private Integer count = 1;
        LinkedList<T> mts = new LinkedList<>();;

        public SyncStack(Integer maxCount){
            this.maxCount = maxCount;
        }
        public Integer getCount() {
            return this.count;
        }
        public synchronized void push(T t){
            while (mts.size()==this.maxCount){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mts.add(t);
            ++count;
            if(count>=11){
                count = 1;
            }
            this.notifyAll();

        }

        public synchronized T pop(){
            while (mts.size()==0){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T t = mts.removeFirst();
            count--;
            if(count <= 1){
                count = 1;
            }
            this.notifyAll();
            return t;
        }
    }
    class Producter extends Thread{
        SyncStack syncStack ;
        String name;
        public Producter(SyncStack syncStack,String name){
            this.syncStack = syncStack;
            this.name = name;
        }
        @Override
        public void run() {
            for (int i = 1; i <= 25; i++) {
                ManTou manTou = new ManTou(name+"生产的"+i+"号馒头");
                syncStack.push(manTou);
                System.out.printf("%s生产馒头%s\n",name,i);
                SleepUtils.SleepMilliseconds(200);
            }
        }
    }
    class Consumer extends Thread{
        SyncStack syncStack ;
        String name;
        public Consumer(SyncStack syncStack,String name){
            this.syncStack = syncStack;
            this.name = name;
        }
        @Override
        public void run() {
            for (int i = 0; i < 5 ; i++) {
                ManTou mt = (ManTou)syncStack.pop();
                System.out.printf("%s消费%s\n",name,mt);
                SleepUtils.SleepMilliseconds(200);
            }
        }
    }
    public void test(){
        SyncStack syncStack = new SyncStack(10);
        for (int i = 0; i < 2; i++) {
            new Producter(syncStack,"生产者"+i+"号").start();
        }
        SleepUtils.SleepMilliseconds(500);
        for (int i = 0; i < 10 ; i++) {
            new Consumer(syncStack,"消费者"+i+"号").start();
        }

    }
    public static void main(String[] args) {
        new MySCZorXFZ().test();
    }
}
