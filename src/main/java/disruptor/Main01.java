package disruptor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class Main01 {
    public static void main(String[] args)throws Exception {
        LongEventFactory factory = new LongEventFactory();//消息工厂
        int bufferSize = 1024;//环形数组大小
        ThreadFactory threadFactory = Executors.defaultThreadFactory();//生产者线程工厂

        Disruptor<LongEvent> disruptor = new Disruptor<>(factory,bufferSize,threadFactory);

        //消费者处理消息
        disruptor.handleEventsWith(new LongEventHandler());


        disruptor.start();//初始化环形数组，数组中每个位置放一个空的Event消息

        //以下所有代码是生产者代码,set环形数组中的消息，等待消费者消费
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();//这个是获取环形数组

        long sequence = 0l;
        LongEvent longEvent = null;
        for (int i = 0; i < 100; i++) {
            sequence=ringBuffer.next();
            longEvent = ringBuffer.get(sequence);
            longEvent.setValue(888L+i);
            ringBuffer.publish(sequence);//发布，之后消费者才能消费
        }
    }
}
