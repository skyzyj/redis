package disruptor;

public class MessageEvent  {
    private String message;
    private long No;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setNo(long no) {
        No = no;
    }

    public long getNo() {
        return No;
    }

    @Override
    public String toString() {
        return "MessageEvent[no="+No+",massage="+message+"]";
    }
}
