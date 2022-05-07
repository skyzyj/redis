package com.itos.redis_demo.agent;

import java.lang.instrument.Instrumentation;

public class ObjectSizeAgent {
    private static Instrumentation inst;

    public static void premain(String agentArgs,Instrumentation _inst){
        inst = _inst;
    }
    public static Long SizeOf(Object o){
        return  inst.getObjectSize(o);
    }
}
