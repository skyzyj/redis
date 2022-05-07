package com.itos.redis_demo.thread.lock;

import java.io.IOException;

public class QiangReference {
    public static void main(String[] args) throws IOException {
        M m = new M(); // new这种申明的对象，m就是的强引用
        m = null;
        System.gc();
        System.in.read();
    }
}
