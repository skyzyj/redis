package threadpool;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * 异步任务处理
 */
public class MyCompletableFuture {
    static Random r = new Random();
    static double priceTM(){
        SleepUtils.SleepMilliseconds(r.nextInt(1000));
        System.out.println(Thread.currentThread().getName());
        return 1.0;
    }
    static double priceTB(){
        SleepUtils.SleepMilliseconds(r.nextInt(1000));
        System.out.println(Thread.currentThread().getName());
        return 2.0;
    }
    static double priceJD(){
        SleepUtils.SleepMilliseconds(r.nextInt(1000));
        System.out.println(Thread.currentThread().getName());
        return 3.0;
    }
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //异步执行各种任务,每个任务开启一个线程，线程异步执行
        //管理多个任务的结果,对各种任务进行组合管理
        CompletableFuture<Double> ctm = CompletableFuture.supplyAsync(()->priceTM());
        CompletableFuture<Double> ctb = CompletableFuture.supplyAsync(()->priceTB());
        CompletableFuture<Double> cjd = CompletableFuture.supplyAsync(()->priceJD());
        //CompletableFuture.anyOf，只有其中一个任务完成则继续
        CompletableFuture.allOf(ctm,ctb,cjd).join();//阻塞的，只有等到所有的任务执行完成才继续
        long end = System.currentTimeMillis();


        System.out.println(end -start);

        //链式处理,前面的结果是后面的输入
        CompletableFuture.supplyAsync(()->priceJD())
                .thenApply(String::valueOf)
                .thenApply(s -> "price: "+s)
                .thenAccept(System.out::println);

    }
}
