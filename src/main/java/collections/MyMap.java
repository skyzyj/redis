package collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashTable ,Collections.synchronizedMap(new HashMap<>())都是线程安全的，且效率差不多
 */
public class MyMap {
    //HashTabel vs Collections.synchronizedMap/synchronizedSet vs ConcurrentHashMap
    //前2个都是使用synchronized实现，效率差不多
    //ConcurrentHashMap写的效率低于前2个，读的效率远远高于前两个
    //static Hashtable<UUID,UUID> m = new Hashtable<>();
    //static Map<UUID,UUID> m = Collections.synchronizedMap(new HashMap<UUID,UUID>());
    static ConcurrentHashMap<UUID,UUID> m = new ConcurrentHashMap<>();
    static int count = CollectionsCount.COUNT;
    static final int THREAD_COUNT = CollectionsCount.THREAD_COUNT;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];

    static {
        for (int i = 0; i < count ; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

   static class MyThread extends Thread{
        int start;
        int gap = count / THREAD_COUNT;

        public MyThread(int start){
            this.start = start;
        }

        @Override
        public void run() {
            for (int i=start; i < start + gap; i++) {
                m.put(keys[i],values[i]);
            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Thread[] ts = new Thread[THREAD_COUNT];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new MyThread(i*(count/THREAD_COUNT));
        }
        for (Thread t:ts) {
            t.start();
        }
        for (Thread t:ts){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(end -start);
        System.out.println(m.size());

        start = System.currentTimeMillis();
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(()->{
                for (int j = 0; j < 10000000; j++) {
                    m.get(keys[10]);
                }
            });
        }
        for (Thread t:ts) {
            t.start();
        }
        for (Thread t:ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }

}
