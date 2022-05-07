package collections;

import java.util.Vector;

/**
 * 容器分2大类：collection和map
 * collection分 list set queue
 *
 * Vector实现List接口，是线程安全的 1.0 基本不用
 * HashTable实现Map接口，是线程安全 1.0 基本不用
 * HashMap实现Map接口，是非线程安全
 * Collections.synchronizedMap(new HashMap())将HashMap变成线程安全
 *ConcurrentHashMap,使用juc里面的锁实现线程安全，读效率更高，写的效率不如HashTable，Collections.synchronizedMap(new HashMap())
 *
 */
public class CollectionsCount {
    static final int COUNT = 1000000;
    static final int THREAD_COUNT = 100;
}
