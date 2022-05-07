package com.itos.redis_demo.thread.lock;

import com.itos.redis_demo.comm.SleepUtils;
import com.sun.org.apache.xerces.internal.util.SAXLocatorWrapper;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 虚引用
 * 用于写JVM，Netty的人用的，用于清除堆外内存
 */
public class MyPhantomReference {
    final static List<Object> LIST = new LinkedList<>();
    final static ReferenceQueue<M> QUEUE = new ReferenceQueue<>();
    final static String a ="0";
    public static void main(String[] args) {
        PhantomReference<M> m = new PhantomReference<>(new M(), QUEUE);

        new Thread(()->{
            while (true) {
                LIST.add(new byte[1024 * 1024]);
                SleepUtils.SleepMilliseconds(200);
                System.out.println(m.get());
            }
        }).start();

        new Thread(()->{
            while (true){
                Reference<? extends M> poll = QUEUE.poll();
                if(poll!=null){
                    System.out.println("虚引用被JVM回收了 "+poll);
                }
            }
        }).start();

        SleepUtils.SleepMilliseconds(500);
    }
}
