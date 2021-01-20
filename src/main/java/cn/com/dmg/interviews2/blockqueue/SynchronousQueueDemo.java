package cn.com.dmg.interviews2.blockqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1.");
                blockingQueue.put("1");

                System.out.println(Thread.currentThread().getName() + "\t put 2.");
                blockingQueue.put("2");

                System.out.println(Thread.currentThread().getName() + "\t put 3.");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + "\t take" + blockingQueue.take());

                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + "\t take" + blockingQueue.take());

                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + "\t take" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }
}
 
//A  put 1.
//3s后
//B  take1
//A  put 2.
//3s后
//B  take2
//A  put 3.
//3s后
//B  take3
