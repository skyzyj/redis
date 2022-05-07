package threadpool;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CachedThreadPool内部使用SynchronizedQueue,最大线程数int的最大值，所以来一个任务必须马上执行，如果没有
 * 空闲线程，立即创建一个线程执行任务。
 * 缺点：这样的机制下，会创建很多个线程，这样的后果1是降低效率，2是CUP被切换线程拖死
 */
public class MyCachedThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);
        for (int i = 0; i < 2; i++) {
            service.execute(()->{
                SleepUtils.SleepMilliseconds(500);
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);
        SleepUtils.SleepMilliseconds(1200);
        System.out.println(service);

    }
}
