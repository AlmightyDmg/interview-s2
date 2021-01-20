package cn.com.dmg.interviews2.blockqueue;

import java.util.concurrent.*;

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("callable...");
        Thread.sleep(2000);
        return 1024;
    }
}

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask, "A").start();
        //如果是同一个task，即使多个线程，也只进call()一次
        new Thread(futureTask, "B").start();
        System.out.println(Thread.currentThread().getName() + "...");

        //这种方式等价于futureTask.get()
//        while(!futureTask.isDone()){
//
//        }

        Integer result = futureTask.get();  //获取callable线程的计算结果，如果没有完成计算，会导致堵塞，直到计算完成
        System.out.println("result：" + result);
    }
}
 
//main...
//callable...
//2s后
//result：1024
