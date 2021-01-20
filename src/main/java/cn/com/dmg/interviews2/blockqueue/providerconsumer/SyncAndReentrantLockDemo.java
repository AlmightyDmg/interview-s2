package cn.com.dmg.interviews2.blockqueue.providerconsumer;

/**
 * 题目：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
 *  A打印5次，B打印10次，C打印15次
 *  紧接着
 *  A打印5次，B打印10次，C打印15次
 *  ……
 *  重复10轮
 */
public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        ShareResource resource = new ShareResource();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print15();
            }
        }, "C").start();
    }
}
//A 1
//A 2
//A 3
//A 4
//A 5
//B 1
//B 2
//B 3
//B 4
//B 5
//B 6
//B 7
//B 8
//B 9
//B 10
//C 1
//C 2
//C 3
//C 4
//C 5
//C 6
//C 7
//C 8
//C 9
//C 10
//C 11
//C 12
//C 13
//C 14
//C 15
//……重复10轮