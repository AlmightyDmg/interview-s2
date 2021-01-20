package cn.com.dmg.interviews2.deadLock;

class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        /**
         * A 线程 进入第一个synchronized获得锁A，然后想要进入第二个synchronized，获得锁A
         * 此时
         * B 线程 进入第一个synchronized获得锁B，然后想要进入第二个synchronized，获得锁A
         *
         * 但是，这时候A线程拿着锁A，B线程拿着锁B，所以两个谁也获取不到，就会造成死锁
         *
         * 第二个synchronized放到第一个synchronized里面是要达到“吃着碗里瞧着锅里”的效果
         *
         */
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t持有" + lockA + ",想要获得" + lockB);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t持有" + lockB + ",想要获得" + lockA);
            }
        }
    }
}

/**
 *
 */
public class DeadLockDemo {

    public static void main(String[] args) throws Exception{
        String lockA = "锁A";
        String lockB = "锁B";
        new Thread(new HoldLockThread(lockA, lockB), "A").start();
        //Thread.sleep(2000);
        new Thread(new HoldLockThread(lockB, lockA), "B").start();
        //new Thread(new HoldLockThread(lockA, lockB), "B").start();

        /**
         * 死锁故障排查：
         * 1.证明是死锁，而不是死循环之类的
         * 使用jps
         *  Linux ps -ef|grep xxxx ls -l
         *  Windows下的java运行程序也有类似ps的查看进程的命令，但是目前我们需要查看的只是java程序
         *  所以是 jps = java ps   jps-l
         * 2.打出java栈的异常信息 jstack
         *
         *
         */

    }
}