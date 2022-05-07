package com.itos.redis_demo.jvm;

import java.io.IOException;

public class T002 {
    public static void main(String[] args) {
        for(;;){
            try {
                System.in.read();
                new T004().m();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
