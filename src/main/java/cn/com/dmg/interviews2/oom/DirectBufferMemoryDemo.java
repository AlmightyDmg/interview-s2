package cn.com.dmg.interviews2.oom;

import java.nio.ByteBuffer;

public class DirectBufferMemoryDemo {

    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory" + (sun.misc.VM.maxDirectMemory() / (double)1024 / 1024) + "MB");
        //设置 -XX:MaxDirectMemorySize=5m
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}