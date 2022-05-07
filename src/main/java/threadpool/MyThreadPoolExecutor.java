package threadpool;

import com.itos.redis_demo.comm.SleepUtils;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * 线程池 ThreadPoolExecutor
 * 继承关系
 *    ThreadPoolExecutor ↑继承 AbstractExecutorService 继承 ExecutorService 继承 Executor
 *                          Executor
 *                            ↑
 *                     ExecutorService
 *                           ↑
 *                 AbstractExecutorService
 *                     ↑            ↑
 *           ThreadPoolExecutor  ForkJoinPool
 * 线程维护了1线程队列（核心线程（不受keepAliveTime影响）+非核心线程），和1任务队列，最大可处理的任务=最大线程数+任务队列
 *
 * 线程池构造函数7大参数
 *  1 核心线程数     启动后核心线程也不会启动
 *  2 最大线程数
 *  3 最大闲置时间
 *  4 闲置时间单位
 *  5 任务队列
 *  6 线程工厂 Executors.defaultThreadFactory()默认的线程工厂，设置了线程名，是否是守护进程，优先级
 *  7 拒绝策略 jdk提供4中(是ThreadPoolExecutor的4内部类)，抛异常，丢弃，丢弃最等待最久的，调用者执行,一般情况下自定义策略,实现RejectedExecutionHandler接口
 * 线程池状态
 *   running (运行) -> shutdown(调用shutdown方法)->
 *   stop(调用shutdownNow方法)->tidying(调用shutdown线程也执行完，在进行最后的整理)->
 *   terminated(关闭)
 *
 */
public class MyThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
                2,//核心线程数
                4,//最大线程数
                60,//线程闲置最大时间,核心线程不受这个限制
                TimeUnit.SECONDS,//闲置时间单位
                new ArrayBlockingQueue<Runnable>(4),//任务队列,必须是阻塞任务队列BlockingQueue
                Executors.defaultThreadFactory(),//线程工厂，产生线程的方法(设置线程名，是否是守护线程daemon,优先级)
                new MyRejectedExecutionHandler("自定义策略")//任务队列满，线程忙，拒绝策略,jdk提供4种，也可以自定义拒绝策略
                                                            //1 Abort 抛异常
                                                            //2 Discard 扔掉后面加入的，不抛异常
                                                            //3 DiscardOldest 扔掉队列里排队时间最久的
                                                            //4 CallerRuns 调用者(调用线程)处理任务
        );

        for (int i = 0; i < 8; i++) {
            tpe.execute(new MyTask(i));
        }
        System.out.println(tpe.getQueue());
        tpe.execute(new MyTask(9));
        System.out.println(tpe.getQueue());
        tpe.shutdown();//用完一定要关掉线程池
    }
    static class MyTask implements Runnable{
        private int i;
        public MyTask(int i){this.i = i;}
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"Task "+i);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{i=" + i + "}";
        }

    }

    static class MyRejectedExecutionHandler implements RejectedExecutionHandler{
        String name ;
        public  MyRejectedExecutionHandler(String name){
            this.name = name;
        }
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //一般情况下，会将任务存放到db,redis或mq中，等待空闲时执行
            // 1 日志记录sl4j
            // 2 保存r到kafuka,mysql,redis
            // 3 试n次
            int i =0;
            while (++i < 100) {
                if (executor.getQueue().size() < 6) {
                    executor.execute(r);
                }
            }
        }
    }
}
