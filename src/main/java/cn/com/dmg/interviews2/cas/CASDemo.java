package cn.com.dmg.interviews2.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS Compare And Swap 比较并交换
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5,2020)+"\t current data:" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5,2021)+"\t current data:" + atomicInteger.get());
    }

}
