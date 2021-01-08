package cn.com.dmg.interviews2.myvolatile;

public class SingletonDemo {
    private static volatile SingletonDemo instance = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "构造方法");
    }


    /**
     * 尽量不要在方法上家synchronized，因为是重锁，降低并发性
     * DCL Double Check Lock 双端加锁机制  在加锁前后都进行判断
     * @return
     */
    public static SingletonDemo getInstance(){

        //单线程写法
//        if (instance == null){
//            instance = new SingletonDemo();
//        }


        if (instance == null){
            synchronized (SingletonDemo.class){
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {
        //单线程玩法
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        //多线程玩法 单线程写法发生了很大的变化
        for (int i = 0; i < 100; i++) {
            new Thread(()->SingletonDemo.getInstance()).start();
        }

    }
}