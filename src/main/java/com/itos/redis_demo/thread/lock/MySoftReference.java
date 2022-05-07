package com.itos.redis_demo.thread.lock;
import com.itos.redis_demo.comm.SleepUtils;

import java.lang.ref.SoftReference;

/**
 * 软引用：只有内存不够用的时候才会被gc清除
 * SoftReference
 */
public class MySoftReference {
    public static void main(String[] args) {
        //软引用，只有内存不够用到时候才会被清除,做缓存用
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println(m.get());
        System.gc();
        SleepUtils.SleepSecondes(3);
        System.out.println(m.get());
        byte[] b = new byte[1024*1024*11];
        System.out.println(m.get());
    }
}
