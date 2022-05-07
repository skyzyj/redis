package jmh;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * jmh测试代码性能
 */
public class PS {
    static List<Integer> list = new ArrayList<>();
    static Random r = new Random();
    static{
        for (int i = 0; i < 1000; i++) {
            list.add(1000000+r.nextInt(100000));
        }
    }

    static void foreach(){
        list.forEach(t->isPreme(t));
    }
    static void paraller(){
        list.parallelStream().forEach(PS::isPreme);
    }

    static boolean isPreme(int v){
        for (int i = 2; i <= v/2; i++) {
            if(v % i == 0){
                return false;
            }
        }
        return true;
    }
}
