package com.itos.redis_demo.jvm;

import io.lettuce.core.output.KeyStreamingChannel;

public class Lambda {
    public static void main(String[] args) {
      /* new Lambda().n(()->{
           System.out.println("hello");
       });*/
       for (;;) {
           Iy iy = C::cn;
       }
    }
    public void n(Iy iy){
        iy.m();
    }
    public interface Iy{
        void m();
    }
    public static class C{
        public static void cn(){
            System.out.println("hello");
        }
    }

}


