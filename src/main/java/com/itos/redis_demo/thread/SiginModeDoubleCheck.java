package com.itos.redis_demo.thread;

public class SiginModeDoubleCheck {
    private volatile SiginModeDoubleCheck INSTANCE; //在这里加volatile是因为new SiginModeDoubleCheck()有4条指令，防止指令重排序
    private SiginModeDoubleCheck(){}

    public SiginModeDoubleCheck getInstance(){
        if(INSTANCE==null){
            synchronized (SiginModeDoubleCheck.class){
                if(INSTANCE==null){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new SiginModeDoubleCheck();
                }
            }
        }
        return INSTANCE;
    }
}
