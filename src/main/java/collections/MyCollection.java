package collections;

import com.itos.redis_demo.comm.SleepUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyCollection {
    //Vector vs Collections.synchronizedList vs Collections.synchronizedSet vs ConcurrentLinkedQueue
    //后者效率远高于前3者,直接用queue即可
    //static Vector<String> list = new Vector<>(); //线程安全
    static List<String> list = Collections.synchronizedList(new LinkedList<String>());//线程安全
    //static Set<String> list = Collections.synchronizedSet(new TreeSet<String>());//线程安全，因为没有顺序所以取数据不能用索引
    static Queue<String> list1 = new ConcurrentLinkedQueue<>();//线程安全

    static {
        for (int i = 0; i <1000 ; i++) {
            list.add("火车票"+i);
            list1.add("火车票"+i);
        }
    }

    public static void main(String[] args) {
       //m();
        m1();
    }
    public static void m1(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true){
                    String s = list1.poll();//不用synchronized，因为只有一个取的动作
                    if(s == null){
                        System.out.println("该窗口销售的票已销售完");
                        break;
                    }else{
                        System.out.println("销售了-->"+s);
                    }
                }
            }).start();
        }
    }
    public static void m(){
        for (int i = 0; i < 10; i++) {
            new  Thread(()->{
                while (true){
                    synchronized (list) { //虽然vector是线程安全的，但是由于list.size和list.remove之间执行代码需要时间
                                           //当只剩下最后一张票时，这个时间段内多个线程判断list.size>0,在取票时后来线程取票发生空值异常
                        if(list.size() <=0 ) break;
                        SleepUtils.SleepMilliseconds(5);
                        System.out.println("销售了-->" + list.remove(0));
                    }
                }
            }).start();
        }
    }
}
