package com.itos.redis_demo.thread;

public  class ThreadSynchronzed implements Runnable{
    private int count = 100;

    //synchronized:保证原子性和可见性
    //volatile:保证可见性
    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName()+" count: "+count);
    }

    public static void main(String[] args) {
        ThreadSynchronzed t = new ThreadSynchronzed();
        for (int i=0;i<100;i++) {
            new Thread(t,"T"+i).start();
        }
    }
}
