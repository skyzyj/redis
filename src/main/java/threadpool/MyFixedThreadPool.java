package threadpool;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.concurrent.*;

/**
 * 固定线程数量线程池 FixedThreadPool  核心线程数和最大线程数一样
 * 内部队列是LinkedBlockingQueue ,所以不建议用，因为会几乎队列是无界的
 * 线程的数量可以是cpu核心数
 * 可以实现并行执行，核心线程必须等于小于CPU核心数
 */
public class MyFixedThreadPool {
    public static void main(String[] args) {
        final int cpuCoreNum = 2;
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);
        MTask m1 = new MTask(1,100);
        MTask m2 = new MTask(101,1000);
        MTask m3 = new MTask(1001,10000);
        MTask m4 = new MTask(10001,100000);
        MTask m5 = new MTask(100001,1000000);

        Future<Integer> rm1 = service.submit(m1);//submit是异步的
        Future<Integer> rm2 = service.submit(m2);
        Future<Integer> rm3 = service.submit(m3);
        Future<Integer> rm4 = service.submit(m4);
        Future<Integer> rm5 = service.submit(m5);

        long start = System.currentTimeMillis();

        try {
            System.out.println(rm1.get()+rm2.get()+rm3.get()+rm4.get()+rm5.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("多线程并行执行时间:"+(end - start));

        start = System.currentTimeMillis();
        new Thread(()->{
            int sum = 0;
            for (int i = 0; i < 1000000; i++) {
                sum += i;
            }
            System.out.println(sum);
        }).start();
        end = System.currentTimeMillis();
        System.out.println("单线程执行时间："+(end - start));
        service.shutdown();
    }

    static class MTask implements Callable<Integer> {
        int start;
        int end;
        public MTask(int start,int end){
            this.start = start;
            this.end = end;
        }
        @Override
        public Integer call() throws Exception {
            int sum =0 ;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
