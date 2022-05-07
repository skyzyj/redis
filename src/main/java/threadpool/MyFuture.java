package threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask = Future + Runnable
 */
public class MyFuture {
    public static void main(String[] args) {
        FutureTask<String> task = new FutureTask<>(()->{
            return "既是一个任务也是存放任务返回值";
        });
        new Thread(task).start();
            try {
                System.out.println(task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
        }
    }
}
