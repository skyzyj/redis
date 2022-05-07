package disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 定义可重复消费者
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public static int count;
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        count ++;
        System.out.println("["+Thread.currentThread().getName()+"]"+longEvent+" 序号:"+l);
    }
}
