package com.itos.redis_demo.jvm;

public class OutOfStackEg {
    public static void main(String[] args){
        for (;;){
            m();
        }
    }
    static void m(){
        m();
    }
}
