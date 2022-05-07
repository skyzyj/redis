package com.itos.redis_demo.stack;

public class MyStack1 {
    public static void main(String[] args) {
        int i = 8;
        i = i++;//代码中++,或+1一定要小心使用
        //i = ++i;
        System.out.println(i);
    }
}
