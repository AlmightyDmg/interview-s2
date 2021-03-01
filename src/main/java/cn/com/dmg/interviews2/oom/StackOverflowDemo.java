package cn.com.dmg.interviews2.oom;

public class StackOverflowDemo {

    public static void main(String[] args) {
        stackOverflow();
    }

    private static void stackOverflow() {
        stackOverflow();
    }
}