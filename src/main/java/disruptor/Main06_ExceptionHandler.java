package disruptor;

import com.itos.redis_demo.comm.SleepUtils;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消费者异常处理,必须要有这种机制，否则出了异常不处理，程序会退出，不是我们所期待的
 *
 */
public class Main06_ExceptionHandler {
    public static void main(String[] args) {
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,bufferSize,
                Executors.defaultThreadFactory(), ProducerType.MULTI,new BlockingWaitStrategy());

        //LongEventExceptionHandler h1 = new LongEventExceptionHandler();
        EventHandler h1 = (event,sequence,enOfBatch)->{
            System.out.println(Thread.currentThread().getName() + event + "序号:"+sequence);
            throw new Exception("消费者出异常");
        };

        disruptor.handleEventsWith(h1);//为disruptor添加消费者
        //===========================以下消费者异常处理======================
        //为消费者h1添加异常处理
        disruptor.handleExceptionsFor(h1).with(new ExceptionHandler() {
            @Override
            public void handleEventException(Throwable throwable, long l, Object o) {
                //throwable.printStackTrace();
                //System.err.println(throwable.getMessage());
                System.err.printf("%s\n\t%s\n\n",throwable.fillInStackTrace().toString(),throwable.getStackTrace()[0]);
            }

            @Override
            public void handleOnStartException(Throwable throwable) {
                System.out.println("Exception start to handle!");
            }

            @Override
            public void handleOnShutdownException(Throwable throwable) {
                System.out.println("Exception shutdown to handle!");
            }
        });

        disruptor.start();
        //==========================以下是生成者code===========================
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
                    long jj = j;
                    ringBuffer.publishEvent((event,sequentce)->{
                        event.setValue(jj);
                       // System.out.println(Thread.currentThread().getName()+"生产了 "+jj);
                    });
                }
            });
        }
        service.shutdown();
        SleepUtils.SleepMilliseconds(200);
        System.out.println(LongEventHandler.count);

        disruptor.shutdown();
    }
}
