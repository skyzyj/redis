package threadpool;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 分叉组合线程池 ,是通过ForkJoinPool实现的
 *   每个线程都有自己的单独的队列,如果有线程完成了自己队列的任务，就会去偷其他线程队列的任务来执行
 *   每个线程执行自己队列的任务不用锁，但是去偷其他线程的任务要用锁
 */
public class MyWorkStealingThreadPoll {
    final static int[] nums = new int[300000];
    final static int FORK = 50000;
    static Random r = new Random();
    static{
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println("单线程执行--"+ Arrays.stream(nums).sum()); //数组求和 单线程计算
    }
    public static void main(String[] args)throws Exception {
        /*ExecutorService service = Executors.newWorkStealingPool();
        service.execute(new R(0,FORK));//无返回值
        service.execute(new R(FORK,FORK*2));
        service.execute(new R(FORK*2,FORK*3));
        service.execute(new R(FORK*3,FORK*4));
        service.execute(new R(FORK*4,FORK*5));
        service.execute(new R(FORK*5,FORK*6));
        System.in.read();*/

        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());
        Future<Long> s1 = service.submit(new RC(0, FORK));//有返回值
        Future<Long> s2 = service.submit(new RC(FORK, FORK * 2));
        Future<Long> s3 = service.submit(new RC(FORK * 2, FORK * 3));
        Future<Long> s4 = service.submit(new RC(FORK * 3, FORK * 4));
        Future<Long> s5 = service.submit(new RC(FORK * 4, FORK * 5));
        Future<Long> s6 = service.submit(new RC(FORK * 5, FORK * 6));
        System.out.println("WorkStealingPool--" +(s1.get()+s2.get()+s3.get()+s4.get()+s5.get()+s6.get()));

    }

    static class R implements Runnable{
        int start;
        int end;
        R(int start,int end){
            this.start = start;
            this.end = end;
        }
        @Override
        public void run() {
            long sum = 0 ;
            for (int i = start; i < end; i++) {
                sum += nums[i];
            }
            System.out.println(sum);
        }
    }
    static class RC implements Callable<Long> {
        int start;
        int end;
        RC(int start,int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public Long call() throws Exception {
            long sum = 0 ;
            for (int i = start; i < end; i++) {
                sum += nums[i];
            }
            System.out.println(Thread.currentThread().getName());
            return sum;
        }
    }
}
