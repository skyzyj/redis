package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors 线程池的工厂
 * SingleThreadPool: 单线程线程池
 * 作用 ：保证任务是按进入线程池的顺序执行的
 * 为什么要有单线程线程池?
 *    线程池维护了任务队列，线程池有线程生命周期管理
 */
public class MySingleThreadPool {
    public static void main(String[] args) {
        ExecutorService singletp = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            singletp.execute(()->{
                System.out.println(Thread.currentThread().getName() + "j=" + j);
            });
        }
    }
}
