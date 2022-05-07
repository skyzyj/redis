package com.itos.redis_demo.fiber;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

public class MyFiber {
    public static void  m(){}
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //纤程的使用
        for(int i=0;i<10000;i++) {
            Fiber<Void> fiber = new Fiber<Void>(new SuspendableRunnable() {
                @Override
                public void run() throws SuspendExecution, InterruptedException {
                    m();
                }
            });
            fiber.start();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
