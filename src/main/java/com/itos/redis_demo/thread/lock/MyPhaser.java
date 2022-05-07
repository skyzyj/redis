package com.itos.redis_demo.thread.lock;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 *线程分阶段执行,设定n个线程一起工作，这个工作时分阶段完成的，
 * 当n个线程都完成了一个阶段,则执行一个总结job,这个job是重写Phaser类的onAdvance方法实现，
 * 怎么判断某个阶段完成的依据是，每个线程执行完自己的当前阶段的工作后调用arriveAndAwaitAdvance方法
 * 每调用一次，Phaser就会记录一个线程完成，当完成是阶段工作的线程数等于Phaser在初始化时设置一个工作执行需要的总线程数，
 * 则自动调用Phaser的onAdvance方法，该方法的第一个参数记录了阶段情况，从0开始依次往上
 *
 */
public class MyPhaser {
    private static MarriagePhaser phaser = new MarriagePhaser();
    private static Random  r = new Random();
    public static void main(String[] args) {
        phaser.bulkRegister(7);//设置完成工作需要的总线程数,如果设置但是开启的线程数不够则程序阻塞
        for(int i=0;i<5;i++){
            new Thread(new Person("P"+i)).start();
        }
        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();
    }

    static class Person implements Runnable{
        private String name;
        public Person(String name){
            this.name = name;
        }
        private void arrive(){
            try {
                Thread.sleep(r.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 到达现场 \n",name);
            phaser.arriveAndAwaitAdvance();//线程完成当前阶段的的任务，并等待进入下一个任务阶段
        }
        private void eat(){
            try {
                Thread.sleep(r.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 吃完了 \n",name);
            phaser.arriveAndAwaitAdvance();
        }
        private void leave(){
            try {
                Thread.sleep(r.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!name.equals("新郎") && !name.equals("新娘")) {
                System.out.printf("%s 离开了 \n", name);
            }
            phaser.arriveAndAwaitAdvance();
        }
        private void hug(){
            if( name.equals("新郎") || name.equals("新娘") ){
                try {
                    Thread.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s入洞房 \n",name);
            }
            phaser.arriveAndAwaitAdvance(); //后面还有阶段如果是使用了phaser.arriveAndDeregister();则会出现异常
        }
        private void baby(){
            if( name.equals("新郎") || name.equals("新娘") ) {
                try {
                    Thread.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s参与生孩子\n", name);
            }
            phaser.arriveAndDeregister();
        }
        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
            baby();
        }
    }
    static class MarriagePhaser extends Phaser{ //要使用phaser，需要重写onAdvance方法，每个阶段的执行逻辑
        public MarriagePhaser(){}
        public MarriagePhaser(int parties){
           super(parties);
        }
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase){
                case 0:
                    System.out.println("人到齐了 :"+registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完饭了 :"+registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有客人离开了 :"+registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束，新郎新娘可以抱抱了 :"+registeredParties);
                    System.out.println();
                    return false;
                case 4:
                    System.out.println("1年后生了小胖子 :"+ registeredParties);
                    System.out.println();
                    return true;
                default:
                    return true;
            }
        }
    }

}

