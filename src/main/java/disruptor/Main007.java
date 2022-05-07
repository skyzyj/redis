package disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

public class Main007 {
    public static void main(String[] args){
        MessageEventFactory factory = new MessageEventFactory();
        int bufferSize = 1024;
        Disruptor<MessageEvent> disruptor = new Disruptor<>(factory,bufferSize, Executors.defaultThreadFactory());

        disruptor.handleEventsWith(new MessageEventHandler());

        disruptor.start();

        RingBuffer<MessageEvent> ringBuffer = disruptor.getRingBuffer();

        //发布消息使用EventTranslator接口,以下是接口的lambda表达式写法
        ringBuffer.publishEvent((event,sequence,message,no)->{event.setMessage(message);event.setNo(no);},"Hello",1l);
        ringBuffer.publishEvent((event,sequence,message,no)->{event.setMessage(message);event.setNo(no);},"World",2l);

        disruptor.shutdown();
    }
}
