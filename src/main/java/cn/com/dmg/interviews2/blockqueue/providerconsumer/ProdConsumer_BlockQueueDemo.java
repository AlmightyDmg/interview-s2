package cn.com.dmg.interviews2.blockqueue.providerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource {
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        System.out.println(blockingQueue.getClass().getName());
        this.blockingQueue = blockingQueue;
    }

    public void myProd() throws Exception {
        String data = null;
        boolean retValue;

        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
            }
            Thread.sleep(1000);
        }
        System.out.println("大老板叫停，" + Thread.currentThread().getName() + "生产结束.");
    }

    public void myConsumer() throws Exception {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t超过2秒没取到蛋糕，消费退出.");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费队列蛋糕" + result + "成功.");
        }
    }

    public void stop() {
        this.FLAG = false;
    }
}

/**
 * volatile/CAS/atomicInteger/BlockQueue/线程交互/原子引用
 */
public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) {
        MyResource resource = new MyResource(new ArrayBlockingQueue<>(3));
        new Thread(() -> {
            try {
                resource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                resource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("5s时间到，停止生产.");
        resource.stop();
    }
}
 
 
//java.util.concurrent.ArrayBlockingQueue
//A  插入队列1成功
//B  消费队列蛋糕1成功.
//A  插入队列2成功
//B  消费队列蛋糕2成功.
//A  插入队列3成功
//B  消费队列蛋糕3成功.
//A  插入队列4成功
//B  消费队列蛋糕4成功.
//A  插入队列5成功
//B  消费队列蛋糕5成功.
//5s时间到，停止生产.
//大老板叫停，A生产结束.
//B  超过2秒没取到蛋糕，消费退出.
