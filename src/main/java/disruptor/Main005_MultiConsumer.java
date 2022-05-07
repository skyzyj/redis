package disruptor;

import com.itos.redis_demo.comm.SleepUtils;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多消费者
 */
public class Main005_MultiConsumer {
    public static void main(String[] args) {
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,
                                                                  bufferSize,
                                                                  Executors.defaultThreadFactory(),
                                                                  ProducerType.MULTI,
                                                                  new YieldingWaitStrategy());

        //初始化多个消费者
        LongEventHandler consumer1 = new LongEventHandler();
        LongEventHandler consumer2 = new LongEventHandler();

        LongWorkHandler consumer3 = new LongWorkHandler();
        LongWorkHandler consumer4 = new LongWorkHandler();

        //disruptor.handleEventsWith(consumer1,consumer2);//(重复消费)给disruptor指定2个消费者
         disruptor.handleEventsWithWorkerPool(consumer3,consumer4);//(不重复消费)给disruptor指定2个消费者

        disruptor.start();

        //===============================以下是生产者生成消息代码===========================================
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        final int threadNum = 2;
        CyclicBarrier barrier = new CyclicBarrier(threadNum);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNum; i++) {
            final int threadNo = i;
            service.submit(()->{
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 5; j++) {
                    final long jj = j;
                    ringBuffer.publishEvent((event,sequentce)->event.setValue(jj));
                }
            });
        }
        service.shutdown();
        SleepUtils.SleepMilliseconds(1000);
        if (LongWorkHandler.count != 0) {
            System.out.println(LongWorkHandler.count);
        }
        if (LongEventHandler.count != 0) {
            System.out.println(LongEventHandler.count);
        }
        disruptor.shutdown();
    }
}
