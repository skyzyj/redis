package collections;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class MyTransferQueue {
    static Thread t1,t2;
    public static void main(String[] args) {
        //用于多线程同步手递手传递数据，传递数据的线程必须要等到接收线程获取到数据才继续执行
        TransferQueue<String> tq = new LinkedTransferQueue<>();

        t1 = new Thread(()->{
            try {
                for (int i = 65; i < 65+26; i++) {
                    Character ci = (char)i;
                    tq.transfer(ci.toString()); //transfer是立即阻塞等待被取走，put是满了再阻塞
                    System.out.println(tq.take());//接收线程阻塞等待
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2 = new Thread(()->{
            try {
                for (int i = 1; i <= 26; i++) {
                    System.out.println(tq.take());//接收线程阻塞等待
                    tq.transfer(i+"");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();

    }
}
