package collections;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyCopyOnWrite {
    public static void main(String[] args) {
        // CopyOnWriteArrayList VS Collections.synchronizedList
        //前者：多线程安全,写的效率低，读的效率高且高于后者, 写的时候加锁，读的时候不加锁
        //后者：多线程安全，读的效率不如前者，写的效率远高于前者,读写都加锁
        List<String> lists = new CopyOnWriteArrayList<>();//适用于读的线程多，写的少
        //List<String> lists = Collections.synchronizedList(new ArrayList<String>());
        Random r = new Random();
        Thread[] ts = new Thread[100];
        for (int i = 0; i < ts.length ; i++) {
            Runnable ra = new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        lists.add("a" + r.nextInt(10000));
                    }
                }
            };
            ts[i] = new Thread(ra);
        }
        long start = System.currentTimeMillis();
        Arrays.asList(ts).forEach(t->t.start());
        Arrays.asList(ts).forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(lists.size());


        for (int i = 0; i < ts.length ; i++) {
            Runnable ra = new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        lists.get(900);
                    }
                }
            };
            ts[i] = new Thread(ra);
        }
        start = System.currentTimeMillis();
        Arrays.asList(ts).forEach(t->t.start());
        Arrays.asList(ts).forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(lists.size());
    }
}
