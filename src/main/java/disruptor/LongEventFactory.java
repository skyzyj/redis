package disruptor;

import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
