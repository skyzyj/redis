package disruptor;


import com.lmax.disruptor.EventHandler;

public class MessageEventHandler implements EventHandler<MessageEvent> {

    @Override
    public void onEvent(MessageEvent messageEvent, long l, boolean b) throws Exception {
        System.out.println("线程:"+Thread.currentThread().getName()+" "+"编码:"+messageEvent.getNo()+"消息:"+messageEvent.getMessage()+" 序号:"+l);
    }
}
