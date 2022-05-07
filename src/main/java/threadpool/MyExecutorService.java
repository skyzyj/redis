package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 继承了Executor接口，同时完善了线程的执行的生命周期
 */
public class MyExecutorService  {
    public static void main(String[] args)
    {
        ExecutorService es = Executors.newCachedThreadPool();
    }
}
