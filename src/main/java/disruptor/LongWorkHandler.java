package disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * 定义不可重复消费者
 */
public class LongWorkHandler implements WorkHandler<LongEvent> {
    public static int count;
    @Override
    public void onEvent(LongEvent longEvent) throws Exception {
        count++;
        System.out.println(longEvent);
    }
}
