package cn.com.dmg.interviews2.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁（递归锁）
 */

class Phone implements Runnable {

    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
    }

    //----------------------------------------------------------------------------------------

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get(){
        //可以写多次,但加几次、解几次 如果加两次解一次则程序会卡死
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked get()");
            set();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked set()");
        } finally {
            lock.unlock();
        }
    }
}

/**
 * demo 1 证明synchronized是可重入锁
 * t1  invoked sendSMS()      t1线程在外层方法获取锁的时候
 * t1  invoked sendEmail()    t1在进入内层方法会自动获取锁
 * t2  invoked sendSMS()
 * t2  invoked sendEmail()
 * --------------------------------------------------------
 * demo 2 证明ReentranLock是可重入锁
 * t3  invoked get()
 * t3  invoked set()
 * t4  invoked get()
 * t4  invoked set()
 */
public class ReenterLockDemo {

    public static void main(String[] args) {

        Phone phone = new Phone();

        new Thread("t1"){
            @Override
            public void run() {
                try {
                    phone.sendSMS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                try {
                    phone.sendSMS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();

        Thread t3 = new Thread(phone, "t3");
        t3.start();

        Thread t4 = new Thread(phone, "t4");
        t4.start();
    }
}
 
 
//t1 invoked sendSMS()
//t1 invoked sendEmail()
//t2 invoked sendSMS()
//t2 invoked sendEmail()
//
//t3 invoked get()
//t3 invoked set()
//t4 invoked get()
//t4 invoked set()
 
