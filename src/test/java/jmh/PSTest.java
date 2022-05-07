package jmh;

import org.openjdk.jmh.annotations.*;

public class PSTest {

    @Benchmark //开启jmh
    @Warmup(iterations = 1,time = 3) //预热
    @Fork(5) //启多少个线程执行
    @BenchmarkMode(Mode.Throughput) //测试模式,Throughput吞吐量每秒执行多少次
    @Measurement(iterations = 1,time = 3) //测试次数
    public void testForeach(){
        PS.paraller();
    }
}
