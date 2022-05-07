package collections;

import com.esotericsoftware.kryo.util.UnsafeUtil;
import sun.misc.Unsafe;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelayQueue {
    //延迟阻塞队列，队列里面装的必须是实现了Delay接口的任务,如:MyTask
    //可以控制队列中任务的顺序
    //先进先出
    //按时间进行任务调度，定时任务调度，倒计时定时任务
    static BlockingQueue<MyTask> m = new DelayQueue<>(); //延迟的阻塞队列
    static Random r = new Random();

    static class MyTask implements Delayed{
        String name;
        long runningTime;
        MyTask(String name,long runningTime){
            this.name = name;
            this.runningTime = runningTime;
        }

        @Override
        public int compareTo(Delayed o) { //用于队列排序
            if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)){
                return  -1;
            }else if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)){
                return 1;
            }else {
                return 0;
            }
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return "["+name + " "+ runningTime+"]" ;
        }
    }

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask("t1",now + 1000);//1秒后该任务运行
        MyTask t2 = new MyTask("t2",now + 2000);//2秒后执行该任务
        MyTask t3 = new MyTask("t3",now + 1500);//1.5秒后执行该任务
        MyTask t4 = new MyTask("t4",now + 2500);
        MyTask t5 = new MyTask("t5",now + 500);

        try {
            m.put(t1);
            m.put(t2);
            m.put(t3);
            m.put(t4);
            m.put(t5);
            System.out.println(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {
                      synchronized (m) {
                          if (m.size() > 0) {
                              System.out.println(Thread.currentThread().getName() + " " + m.take());
                          } else {
                              System.out.println(Thread.currentThread().getName() + " [我来迟了]");
                          }
                      }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"thread-"+i).start();
        }
    }
}
