package com.itos.redis_demo.thread;

public class InterruptAndSync {
    public static void main(String[] args) {
        /*Thread t1 = new Thread(()->{
            try {
                Thread.sleep(4000);
                System.out.println("hello");
            } catch (InterruptedException e) {
                System.out.println("线程在执行中被标记打断");
                return;
            }
            System.out.println("wold");
        });
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();*/

        Thread t2 = new Thread(()->{
            for (;;){
                if (Thread.interrupted()){
                    break;
                }
            }
        });
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();//线程被设了打断标记后，如果在线程中有正在wait,sleep,会捕获到InnterruptedException异常
    }                  //如果没有wait,sleep,不影响线程执行，除非在现在判断是否线程被标记了打断
                       //线程被标记了打断，synchronized抢占锁时不会抛异常
}
