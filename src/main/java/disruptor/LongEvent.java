package disruptor;

public class LongEvent  {
    private long value;

    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "LongEvent{value="+value+"}";
    }
}
