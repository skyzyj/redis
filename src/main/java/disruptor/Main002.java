package disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main002 {
    public static void main(String[] args) {
        LongEventFactory factory = new LongEventFactory();//消息工厂
        int bufferSize = 1024;
        ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;//消费者线程工厂（后台线程）

        Disruptor<LongEvent> disruptor = new Disruptor<>(factory,bufferSize,threadFactory);

        for (int i = 0; i < 100; i++) {
            disruptor.handleEventsWith(new LongEventHandler());
        }

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        //不带参数的EventTranslator，set固定的消息
        EventTranslator<LongEvent> translator1 = new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent longEvent, long l) {
                longEvent.setValue(8888L);
            }
        };//set初始化的event
        ringBuffer.publishEvent(translator1);//发布消息

        //带一个参数的EventTranslator,发布是set消息
        EventTranslatorOneArg<LongEvent,Long> translator2 = new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Long aLong) {
                longEvent.setValue(aLong);
            }
        };
        ringBuffer.publishEvent(translator2,9999l);//法布的时候set消息

        //带2个参数的EventTranslator,发布时用2个参数来set消息
        EventTranslatorTwoArg<LongEvent,Long,Long> translator3 = new EventTranslatorTwoArg<LongEvent, Long, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Long aLong, Long aLong2) {
                longEvent.setValue(aLong + aLong2);
            }
        };
        ringBuffer.publishEvent(translator3,1000l,2000l);

        //带3个参数的EventTranslator,发布时用3个参数set消息
        EventTranslatorThreeArg<LongEvent,Long,Long,Long> translator4 = new EventTranslatorThreeArg<LongEvent, Long, Long, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Long aLong, Long aLong2, Long aLong3) {
                longEvent.setValue(aLong+aLong2+aLong3);
            }
        };
        ringBuffer.publishEvent(translator4,1000l,2000l,3000l);

        //带动态个参数的EventTranslator，发布时传动态个参数来set消息
        EventTranslatorVararg<LongEvent> translaotr5 = new EventTranslatorVararg<LongEvent>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Object... objects) {
                long sum = 0l;
                for (Object o:objects) {
                    sum += (long)o;
                }
                longEvent.setValue(sum);
            }
        };
        //不固定个参数
        ringBuffer.publishEvent(translaotr5,100l,200l,300l,400l,500l,600l);

    }
}
