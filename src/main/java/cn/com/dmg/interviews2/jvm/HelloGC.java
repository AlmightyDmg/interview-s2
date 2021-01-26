package cn.com.dmg.interviews2.jvm;


/**
 * 如何查看一个正在运行中的java程序，他的某个jvm参数是否开启？具体值是多少
 * jps -l查看进程
 * jinfo 正在运行种的java程序的各种信息
 */
public class HelloGC {
    public static void main(String[] args) throws Exception{
        System.out.println("Hello GC");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
