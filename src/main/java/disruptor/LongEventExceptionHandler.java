package disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventExceptionHandler implements EventHandler<LongEvent> {
    public static int count;
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        count ++;
        System.out.println("["+Thread.currentThread().getName()+"]"+longEvent+" 序号:"+l);
        throw new Exception("消费者异常");
    }
}
