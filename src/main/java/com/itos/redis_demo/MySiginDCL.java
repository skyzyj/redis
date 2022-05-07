package com.itos.redis_demo;

//双重检查实现单例模式
public class MySiginDCL {
    public static volatile MySiginDCL INSTANCE; //双重检查的单例必须要加上volatile，防止指令重排

    private MySiginDCL(){}

    public static MySiginDCL getInstance(){
        if (INSTANCE == null){
            synchronized (MySiginDCL.class){
                if(INSTANCE == null){
                    INSTANCE = new MySiginDCL();
                }
            }
        }
        return INSTANCE;
    }
}
