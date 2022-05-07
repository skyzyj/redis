package threadpool;


import com.itos.redis_demo.comm.SleepUtils;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *定时任务线程池 ScheduledThreadPool
 *  需要指定核心线程数
 *  内部是队列是DelayedWorkQueue 定时任务队列
 *
 */
public class MyScheduledThreadPool {
    public static void main(String[] args) {
        Random r = new Random();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.scheduleAtFixedRate(()->{
                    SleepUtils.SleepMilliseconds(1000);
                    System.out.println(Thread.currentThread().getName());
                },//实现runnable接口
                0,//第一个任务执行前需要往后推多少时间
                1000,//间隔时间多少时间执行一次任务
                 TimeUnit.MILLISECONDS//时间单位
                );
    }
}
