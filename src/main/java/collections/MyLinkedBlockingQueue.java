package collections;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列
 */
public class MyLinkedBlockingQueue {
    //BlockingQueue: 阻塞队列 put,take是它在Queue的基础上特有的API
    //BlockingQueue的put和take实现了生产者消费者模型
    static BlockingQueue<String> m = new LinkedBlockingQueue<>();//线程安全，无界队列(未到内存满都可以装)
                                                                  //可以设置初始化是设置队列大小capacity
    static Random r = new Random();

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                try {
                    m.put("a"+i); //添加元素,如果不能装了，该线程阻塞
                    //SleepUtils.SleepMilliseconds(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                for(;;){
                    try {
                        System.out.println(Thread.currentThread().getName()+m.take());//take是取出元素，如果空了就阻塞
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"thread-"+i).start();
        }
    }
}
