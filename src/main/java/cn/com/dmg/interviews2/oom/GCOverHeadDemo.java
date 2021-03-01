package cn.com.dmg.interviews2.oom;

import java.util.ArrayList;
import java.util.List;

public class GCOverHeadDemo {
    public static void main(String[] args) {
        int i = 0;
        List list = new ArrayList();
        try{
            while (true){
                //把List中对象越来越多 将虚拟机内存干满
                list.add(String.valueOf(++i).intern());
            }
        } catch (Exception e){
            System.out.println(i);
            e.printStackTrace();
        }
    }
}
