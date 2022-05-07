package com.itos.redis_demo.thread;

/**
 * synchronized(Object),Object不能使String常量，以及Integer,Long等这些基础数据类型保装类
 *
 * synchronized底层实现
 * jdk早期，synchronized是重量级的，每次都要去找操作系统申请锁
 * 改进：锁升级(jdk1.5以后)
 * 锁定对象：sync(object),以下锁升级的3个过程
 * 1偏向锁：第一个线程申请锁时，在object上markword,记录这个线程ID-->偏向锁，其实是没有加锁的
 * 2自旋锁： 如果有线程争用把锁，升级为自旋锁
 * 3系统锁： 自旋10次以后，如果还是没有获得该锁，申请为重量级锁(系统锁)
 *
 * 重量级锁就是系统锁：当线程在申请锁时，如果锁对象已经被其他线程占有，
 *                    则锁对象升级为自旋锁,自旋10次后如果还是没有申请到锁，锁升级为系统锁
 *                    当锁升级为系统锁时，线程就进入等待队列，不再占用CPU资源
 *
 * 执行时间短(加锁代码)，线程数量少用自旋锁，因为自旋锁(CAS)需要占用CPU
 * 执行时间长(加锁代码)，线程数量多用重量级锁,因为重量级锁(synchronized)不消耗CPU
 *
 * Lock底层是使用自旋锁方式实现
 *
 * 锁定方法和非锁定方法可以同时执行
 */
public class SynchronizedDCSX {
}
