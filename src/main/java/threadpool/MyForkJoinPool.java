package threadpool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * 分叉，汇总线程池 ForkJoinPool , 和ThreadPoolExecutor一样都是继承自AbstractExecutorService
 *   将一个大的任务先分叉执行，执行完后汇总
 *   ForkJoinPool线程池可执行的任务不是实现Runnable和Callable接口,而是需要继承ForkJoinTask类
 * ThreadPoolExecutor 与 ForkJoinPool的区别
 *   前者所有的线程共享一个任务队列
 *   后者每个线程拥有自己的任务队列
 */
public class MyForkJoinPool {
    final static int[] nums = new int[1000000];
    final static int FORK = 50000;
    static Random r = new Random();
    static{
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println("单线程执行--"+Arrays.stream(nums).sum()); //数组求和 单线程计算
    }
    static class MyRecursiveAction extends RecursiveAction{
        int start;
        int end;
        MyRecursiveAction(int start,int end){
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if((end - start) < FORK){
                int sum = 0 ;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                System.out.println("form:"+start + " to:"+end+" sum: "+sum);
            }else{
                int mid = start + (end -start)/2;
                MyRecursiveAction m1 = new MyRecursiveAction(start,mid);
                MyRecursiveAction m2 = new MyRecursiveAction(mid,end);
                m1.fork();
                m2.fork();
            }
        }
    }

    static class MyRecursiveTask extends RecursiveTask<Long>{
        int start;
        int end;
        MyRecursiveTask(int start,int end){
            this.start = start;
            this.end = end;
        }
        @Override
        protected Long compute() {
            if((end - start) < FORK){
                System.out.println(Thread.currentThread().getName());
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                //System.out.println("form:"+start + " to:"+end+" sum: "+sum);
                return sum;
            }else{
                System.out.println(Thread.currentThread().getName());
                int mid = start + (end -start)/2;
                MyRecursiveTask m1 = new MyRecursiveTask(start,mid);
                MyRecursiveTask m2 = new MyRecursiveTask(mid,end);
                m1.fork();//分叉
                m2.fork();//分叉
                return m1.join() + m2.join(); //合并
            }
        }
    }
    public static void main(String[] args)throws Exception {
        /*ForkJoinPool  fjp = new ForkJoinPool();
        MyRecursiveAction m = new MyRecursiveAction(0,nums.length);
        fjp.execute(m);
        System.in.read();*/

        ForkJoinPool  fjp = new ForkJoinPool(3);
        MyRecursiveTask m1 = new MyRecursiveTask(0,nums.length);
        /*fjp.execute(m1);
        Long result = m1.join();*/
        ForkJoinTask<Long> submit = fjp.submit(m1);
        Long result = submit.get();
        System.out.println("ForkJoinPool--"+result);
    }
}
