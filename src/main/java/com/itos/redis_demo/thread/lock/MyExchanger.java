package com.itos.redis_demo.thread.lock;

import java.util.concurrent.Exchanger;

/**
 *交换器 2个线程之间的数据交换
 * exchange()方法是阻塞的，只有2个线程都将交换的值算出来了才继续
 *
 */
public class MyExchanger {
    static Exchanger<String> exchanger = new Exchanger();
    public static void main(String[] args) {
        new Thread(()->{
            String s11 = "T1";
            String s12 ="T1--第二次交换";
            try {
                s11 = exchanger.exchange(s11);
                s12 = exchanger.exchange(s12);
                System.out.println("线程"+Thread.currentThread().getName()+" s11 = "+s11);
                System.out.println("线程"+Thread.currentThread().getName()+" s12 = "+s12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            String s21 = "T2";
            String s22 ="T2--第二次交换";
            try {
                s21 = exchanger.exchange(s21);//可以多次交换
                s22 = exchanger.exchange(s22);
                System.out.println("线程"+Thread.currentThread().getName()+" s21 = "+s21);
                System.out.println("线程"+Thread.currentThread().getName()+" s22 = "+s22);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }

}
