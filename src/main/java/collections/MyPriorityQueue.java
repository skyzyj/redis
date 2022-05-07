package collections;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 排序队列
 */
public class MyPriorityQueue {
    public static void main(String[] args) {
        //自动排序队列
        PriorityQueue<String> q = new PriorityQueue<>(); //非阻塞自动排序队列
        PriorityBlockingQueue<String> qbq = new PriorityBlockingQueue<>();//阻塞自动排序队列
        q.offer("a");
        q.offer("d");
        q.offer("e");
        q.offer("b");
        q.offer("c");



        System.out.println(q);
        for (int i = 0; i < 5; i++) {
            System.out.println(q.poll());
        }
    }
}
