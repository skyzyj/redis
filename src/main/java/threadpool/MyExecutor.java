package threadpool;

import java.util.concurrent.Executor;

/**
 * Executor -> ExecutorService -> ThreadPool
 */
public class MyExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public static void main(String[] args) {
        MyExecutor me = new MyExecutor();
        me.execute(()->{
            System.out.println(Thread.currentThread().getName()+" executor的execute方法");
        });
    }
}
