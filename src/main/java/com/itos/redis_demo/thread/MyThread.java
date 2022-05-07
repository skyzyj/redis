package com.itos.redis_demo.thread;

import java.util.concurrent.*;

/**
 * 创建线程的5种方法
 * 线程6种状态
 * 1 new : 线程刚创建还没启动
 * 2 rannable: 线程颗运行状态，分为ready和running2种状态
 * 3 waiting: 线程等待中被唤醒
 * 4 timed waiting: 隔段时间自动唤醒
 * 5 blocked: 阻塞状态，正在等待锁,只有等待同步锁synchronized才是这个状态
 * 6 treminated: 线程结束
 *
 * 线程打断interrupt
 *
 *
 */
public class MyThread {

// 1 继承Thread ,new T1Thread()创建
    static class T1Thread extends Thread {
        @Override
        public void run() {
            System.out.println("继承方式创建线程");
        }
    }
//2 实现Runnable接口，new Thread（new T1Thread()）创建
    static class T2Runnable implements Runnable{

        @Override
        public void run() {
            System.out.println("实现Runnable接口，new Thread时使用改类的实例构造");
        }
    }
//5 实现Callable<返回值类型>,带返回值的线程
    static class T3Callable implements Callable<String>{
        @Override
        public String call() throws Exception {
            return "返回值";
        }
}
    public static void main(String[] args)throws Exception {
        new T1Thread().start();
        new Thread(new T2Runnable());
        new Thread(()->{
            System.out.println("lambda表达式");
        });

        FutureTask<String> futureTask = new FutureTask<>(new T3Callable());
        new Thread(futureTask);
        futureTask.get();//阻塞获取线程返回的结果


        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()->{
            System.out.println("线程池方式创建线程");
        });
        Future<String> f= service.submit(new T3Callable());//Future是异步的
        String s = f.get(); //获取线程的结果，这个方法是阻塞的
        service.shutdown();

    }
}
