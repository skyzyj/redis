package com.itos.redis_demo.thread;

public class VolatileReference {
    private  A a = new A();
    static volatile VolatileReference v = new VolatileReference();
    public static void main(String[] args) {


        Thread t1 = new Thread(()->{
            while (v.a.running){

            }
            System.out.println(v.a.mes);
            System.out.println("end");
        });
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            System.out.println("t2");
            v.a.running = false;
            v.a.mes = "hello";
        },"t2").start();

    }
}
 class A{
    boolean running = true;
    String mes;
}
