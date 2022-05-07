package collections;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

public class MyConcurrentMap {
    public static void main(String[] args) {
        //ConcurrentHashMap vs ConcurrentSkipListMap
        //后者效率查询效率高，前者插入效率高
        //后者排了序，前者没有排序
        ConcurrentHashMap<String,String> m1 = new ConcurrentHashMap<>();//多线程安全，没有排序
        ConcurrentSkipListMap<String,String> m2 = new ConcurrentSkipListMap<>();//多线程安全，有排序，使用跳表实现

        Random r = new Random();
        Thread[] ts = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    m2.put("a"+r.nextInt(100000),"a"+r.nextInt(100000));
                }
                latch.countDown();
            });
        }
        long start = System.currentTimeMillis();
        Arrays.asList(ts).forEach(a->a.start());

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);


        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    m2.get("a29322");
                }
                latch.countDown();
            });
        }
        start = System.currentTimeMillis();
        Arrays.asList(ts).forEach(a->a.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        System.out.println(end -start);

    }
}
