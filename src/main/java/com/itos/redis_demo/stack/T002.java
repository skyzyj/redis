package com.itos.redis_demo.stack;

public class T002 {
    public static void main(String[] args) {
        I i = C::n;
    }
    @FunctionalInterface
    public interface I{
        void m();
    }
    public static class C{
        static void n(){
            System.out.println("hello");
        }
    }
}
