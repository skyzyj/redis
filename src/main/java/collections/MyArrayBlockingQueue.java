package collections;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyArrayBlockingQueue {
    //线程安全，有界队列
    //必须设置初始化是设置队列大小capacity
    static BlockingQueue<String> m = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws Exception{
        //**************BlockingQueue添加，删除，取元素方法及3种方法的区别**********
        //========异常处理添加，删除失败============== remove--add
        //m.remove(); //队列没有元素remove,抛NoSuchElement异常 remove -- add
        //========返回值处理添加，删除失败============offer--(poll,peek)
        System.out.println(m.poll());//队列没有元素poll不抛异常,返回null   poll -- offer
        System.out.println(m.peek());//队列没有元素poll不抛异常,返回null   peak -- offer
        //=========阻塞处理添加，删除失败==============put--take
        //m.take();//队列没有元素take,线程阻塞  take -- put
        for (int i = 0; i < 10; i++) {

                //m.put("a"+i);
                m.offer("a"+i);
        }
        // m.add("a11");//队列满了继续添加元素，会抛 queue full 异常
        // m.put("a11");//队列满了继续添加元素线程阻塞
        m.offer("a10");//队列满了继续添加线程不会阻塞，但是添加会失败，不会抛异常,程序继续
        //m.offer("a12",1, TimeUnit.SECONDS);//在1秒内尝试添加，成功就返回true，反之返回false


        System.out.println(m);
    }
}
