package com.itos.redis_demo.thread;

public class VolatileStopThread {
    private static volatile boolean running = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            long i = 0l;

            while (running){
                i++;
            }
            System.out.println(i);
        });
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = false;
    //使用innterrupt标记停止线程
        Thread t2 = new Thread(()->{
            while (!Thread.interrupted()){//判断线程是否被标记停止，如果被标记则结束

            }
            System.out.println("t2 end");
        });
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }
}
