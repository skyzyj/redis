package com.itos.redis_demo.thread.lock;

public class M {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}
