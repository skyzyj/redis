package com.itos.redis_demo.jvm;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class T003 {
    public static void main(String[] args) {
        List list = new LinkedList();

        for(;;){
            try {
                TimeUnit.SECONDS.sleep(3);
                list.add(new byte[10*20]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
