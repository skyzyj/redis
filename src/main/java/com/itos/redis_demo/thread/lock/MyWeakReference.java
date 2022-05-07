package com.itos.redis_demo.thread.lock;

import java.lang.ref.WeakReference;

/**
 * 弱引用 WeakReference
 * 只要遭遇到gc就会被回收
 * 一般用在容器
 */
public class MyWeakReference {
    public static void main(String[] args) {
        WeakReference<M> m=new WeakReference<>(new M());
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        //ThreadLocal的set，是放在当前线程对应的map里，map的key是使用弱引用指向tl对象
        //当tl响应的代码执行完后，发生了gc，但是当前线程还没有结束，
        // 当前线程的map肯定也是存在的，map的key如果使用了强引用指向tl,tl就不能能被回收
        //其实这时，tl对应已经没有用处了，所以使用弱引用就解决了这个问题
        ThreadLocal<M> tl = new ThreadLocal<>();//用到了弱引用,
        tl.set(new M());
        tl.remove();//使用ThreadLocal,必须要remove，否则会发生内存泄漏
    }
}
