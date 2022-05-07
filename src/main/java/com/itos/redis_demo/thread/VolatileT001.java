package com.itos.redis_demo.thread;

import org.omg.PortableServer.THREAD_POLICY_ID;

public class VolatileT001 {
    volatile boolean running = true;

    void m(){
        System.out.println("m start");
        while (running){

        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        VolatileT001 vt =new VolatileT001();
        new Thread(vt::m,"t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            System.out.println("t2 start");
            vt.running = false;
            System.out.println("t2 end");
        },"t2").start();

    }
}
