package cn.com.dmg.interviews2.myvolatile;


import java.util.concurrent.atomic.AtomicInteger;

class MyData{
    volatile int number = 0;
    Object object = new Object();

    public void addTo60(){
        this.number = 60;
    }

    //public synchronized void addPlusPlus(){ 不使用synchronized是因为杀鸡焉用牛刀
    public void addPlusPlus(){
        //number已经被volatile修饰，但是不能保证原子性
        this.number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}

/**
 * 验证volatile的可见性
 * 1.当number未被volatile修饰时，new Thread将number值改为60，但main线程并不知道，会一直在循环中出不来
 * 2.当number使用volatile修饰，new Thread改变number值后，会通知main线程主内存的值已被修改，结束任务。体现了可见性
 *
 * 验证volatile不保证原子性
 * 1.原子性是指，某个线程在执行某项业务时，中间不可被加塞或分割，需要整体完整。要么同时成功，要么同时失败
 *
 * 如何解决呢？
 * 1.使用synchronize（不要杀鸡用牛刀，高射炮打蚊子）
 * 2.使用JUC下的AtomicInteger
 *
 */
public class VolatileDemo {
    public static void main(String[] args) {
        //seeByVolatile();
        for (int i = 0; i < 100; i++) {
            atomic();
        }


    }

    //验证原子性
    public static void atomic() {
        MyData myData = new MyData();
        /**
         * 20个线程，每个线程对number+1000，
         * 正常最后结果是：20000
         */
        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 1; j <= 1000; j++) {
                        /*synchronized (myData.object){
                            myData.addPlusPlus();
                        }*/
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }).start();
        }

        //
        /**
         * 等待上面20个线程全部计算结束
         *
         * 这里视频中讲解的是要大于2，解释为有main线程和GC两个线程
         * 但是实际情况是，只有一个main线程，验证代码如下，因此改为大于1
         *
         */
        //System.out.println(Thread.activeCount());
        //Thread.currentThread().getThreadGroup().list();
        while (Thread.activeCount() > 1){
            Thread.yield();
        }


        //System.out.println(Thread.activeCount());

        System.out.println(Thread.currentThread().getName() + "int finally number is " + myData.number);
        System.out.println(Thread.currentThread().getName() + "AtomicInteger finally number is " + myData.atomicInteger);
    }





    //验证可见性的方法
    public static void seeByVolatile() {
        MyData myData = new MyData();
        //第一个线程
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " come in");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + " update number to " + myData.number);
        },"AA").start();

        /**
         * main线程
         * 因为AA线程会阻塞3秒，因此会先到这个循环，
         * 如果不加volatile修饰，那么number的值在其它线程发生改变，也不会通知main线程，因此main线程会一直循环
         * 加上volatile之后，保证了可见性，number在其它线程发生改变之后，会相互进行通知，保证了JMM中的可见性
         */
        while (myData.number == 0){

        }

        System.out.println(Thread.currentThread().getName() + "mission is over");
    }
}


