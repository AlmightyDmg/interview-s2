package cn.com.dmg.interviews2.reference;

import java.util.HashMap;
import java.util.WeakHashMap;

public class WeakHashMapDemo {

    public static void main(String[] args) {
        myHashMap();
        System.out.println("--------------------");
        myWeakHashMap();
    }

    public static void myHashMap() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";

        hashMap.put(key, value);
        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap + "\t" + hashMap.size());
    }

    public static void myWeakHashMap() {
        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "WeakHashMap";

        weakHashMap.put(key, value);
        System.out.println(weakHashMap);

        key = null;
        System.out.println(weakHashMap);

        System.gc();
        System.out.println(weakHashMap + "\t" + weakHashMap.size());
    }
}