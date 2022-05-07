package disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * 使用lambda表达式格式代码
 */
public class Main003 {
    public static void main(String[] args) {
        int bufferzise = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new,bufferzise, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith((event,sequence,endOfbatch)->{ // EventHandler接口的OnEvent方法lambda表达式写法
            System.out.println("["+Thread.currentThread().getName()+"]"+event+" 序号:"+sequence);
        });

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //无参EventTranslator
        ringBuffer.publishEvent((event,sequentce)-> event.setValue(1000l));

        //EventTranslatorOneArg，objects根据，publish后面设置的值确定类型，如果只设置一个值，l1
        ringBuffer.publishEvent(((event,sequence,l1)->event.setValue(l1)),100l);

        //EventTranslatorVararg,objects根据，publish后面设置的值确定类型，如果只设置2个以上的值，objects是Object[]
        ringBuffer.publishEvent(((event,sequence,objects)->event.setValue((long)objects[0] + (long)objects[1]+(long)objects[2])),100l,200l,300l);

        //EventTranslatorTwoArg,如果显示的使用l1,l2,则l1,l2就是是long
        ringBuffer.publishEvent(((event,sequence,l1,l2)->event.setValue(l1+l2)),100l,200l);

        //EventTranslatorThreeArg,如果显示的使用l1,l2,l3则l1,l2,l3就是是long
        ringBuffer.publishEvent(((event,sequence,l1,l2,l3)->event.setValue(l1+l2+l3)),100l,200l,300l);
    }
}
