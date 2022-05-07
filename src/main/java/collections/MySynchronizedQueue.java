package collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class MySynchronizedQueue {
    public static void main(String[] args) {
        //2个线程之间手递手交换数据用到
        BlockingQueue<String> q = new SynchronousQueue<>();// 容量为0

        new Thread(()->{
            try {
                for (int i = 0; i < 3; i++) {
                    System.out.println(q.take());//阻塞等待队列put,发现有put立马取出来,继续等待put
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                for (int i = 0; i < 10; i++) {
                    q.offer("aa"+i);
                    //q.put("aa"+i);//只能阻塞put,等待take,put一次就必须的take之后才能继续put
                    System.out.println(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();



    }
}
