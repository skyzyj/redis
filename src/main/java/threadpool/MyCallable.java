package threadpool;

import java.util.concurrent.*;

public class MyCallable {
    public static void main(String[] args) {
        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "线程执行结果返回值";
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        //Future用于存放线程将来执行的结果
        Future<String> f = service.submit(c);//异步执行，代码不会在这里阻塞等待线程的执行
        try {
            System.out.println(f.get());//get是阻塞的
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
