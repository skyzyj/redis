package collections;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Queue和list的区别:
 *   Queue添加了线程友好的API offer peek poll
 *   Queue的子类型BlockingQueue又添加put,take实现了生产者消费者模型的API
 * BlockingQueue和ConcurrentQueue的区别
 *   前者是阻塞队列，后者是非阻塞队列
 *
 */
public class MyConcurrentQueue {
    public static void main(String[] args) {
        Queue<String> q = new ConcurrentLinkedQueue<>();//线程安全
        for (int i = 0; i < 10; i++) {
            q.offer("a" + i); // 添加元素到队列,加成功了返回true
        }
        System.out.println(q);
        System.out.println(q.size()); //获取队列的长度

        System.out.println(q.poll()); //移除并获取队列头元素
        System.out.println(q.size());

        System.out.println(q.peek()); //获取队列头元素
        System.out.println(q.size());

    }


 }
