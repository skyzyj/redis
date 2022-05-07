package com.itos.redis_demo.thread;

/**
 * sysnchronized是 可重如入的，同一个线程调用加了同一把锁的多个方法是不需要多次申请锁的，申请一次即可
 */
public class SynchronizedKCR {
    private synchronized void m1(){
        System.out.println("m1 start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();//多个方法加了相同的锁，对于同一个线程来讲，只需要申请一次就可以调用
             //加了相同同锁的所有方法，否则就是发生死锁
        System.out.println("m1 end");
    }
    //synchronized修饰静态方法时类似synchronized(当前类名.class)
    //synchronized修饰非静态方法时类似synchronized(this)
    //继承重写如果都被synchronized修饰，且在重写方法调用了类的该方法，则锁对象都是子类的this
    private synchronized void m2(){
        System.out.println("m2 stare");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public static void main(String[] args) {
        new SynchronizedKCR().m1();
    }
}
