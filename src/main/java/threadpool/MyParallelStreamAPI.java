package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyParallelStreamAPI {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            list.add(1000000+r.nextInt(1000000));
        }

        long start = System.currentTimeMillis();
        list.forEach(v -> isPrime(v));
        //list.forEach(MyParallelStreamAPI::isPrime);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        //list.parallelStream().forEach(MyParallelStreamAPI::isPrime);
        list.parallelStream().forEach(v->isPrime(v));//并行流处理,使用的ForkJoinPool
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    private static boolean isPrime(int v){//判断是否是质数
        for (int i = 2; i < v/2; i++){
            if(v % i == 0) {
                return false;
            }
        }
        return true;
    }
}
