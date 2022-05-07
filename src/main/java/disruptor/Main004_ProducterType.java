package disruptor;

import com.itos.redis_demo.comm.SleepUtils;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单生产者ProducerType.SINGLE,默认是多生产者，ProducerType.MULTI
 */
public class Main004_ProducterType {
    public static void main(String[] args) {
        int bufferSize = 1024;

        //指定生产者为单线程ProducerType.SINGLE,默认为多线程ProducerType.MULTI
        //如果生产者模式设置为单线程，但是却用多线程来生成，则生成的数据会被覆盖，生成数量达不到预期
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new,bufferSize, Executors.defaultThreadFactory(),
                ProducerType.SINGLE,new BlockingWaitStrategy());
        //等待策略：生产者的等待策略,disruptor提供了8种 1,7,8 最常用
        //1 BlockingWaitStrategy 生产者线程阻塞，等待被唤醒，唤醒后循环检测环形对列里的消息是否被消费
        //2 BusySpinWaitStrategy 线程一直自旋等待，可能比较耗CPU
        //3 LiteBlockingWaitStrategy  在BlockingWaitStrategy进一步优化
        //4 LiteTimeoutBlockingWaitStrategy 在LiteBlockingWaitStrategy进一步优化
        //5 PhasedBackoffWaitStrategy
        //6 TimeoutBlockingWaitStrategy  设等待时间，超过了抛异常
        //7 YieldingWaitStrategy 尝试100次，然后线程进入yield,让出cpu
        //8 SleepingWaitStrategy sleep等待
        disruptor.handleEventsWith(new LongEventHandler());

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        //因为设置了单生成者，以下使用多线程作为生产者，达不到预期，但是不会报错
        final int threadNum = 50;
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
                for (int j = 0; j < 100; j++) {
                    ringBuffer.publishEvent((event,sequentce)->event.setValue(threadNo));
                }
            });
        }
        service.shutdown();
        SleepUtils.SleepMilliseconds(200);
        System.out.println(LongEventHandler.count);
    }



}
