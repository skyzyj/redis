package com.itos.redis_demo.thread;

/**
 * volatile: 保证线程本地缓存之间的某些数据一致性，线程和内存中的某些数据同步
 *           保证指令不重排序
 */
public class ThreadVisibitity {
    // volatile 保存线程可见性，对于公共数据，线程会拷贝一份到线程本地，
    // 当其线程在他本地改了其中某些值后,会立马刷新到主内存，
    // 如果不加volatile，其他线程用到该值时，会一直用他本地的拷贝
    // 如果使用volatile修饰了数据，使用改数据时，每次都要去主内存读一次，这就是线程的可见性
    private static volatile boolean  running = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            System.out.println("m start");
            while (running){
                //System.out.println("1"); //System.out.println方法也会强制线程重写读一次主内存
            }
            System.out.println("m end");
        });
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = false;
    }
}
