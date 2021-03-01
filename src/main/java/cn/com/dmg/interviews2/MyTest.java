package cn.com.dmg.interviews2;

public class MyTest {
    public static void main(String[] args) {
        String a = new String("1");
        String b = new String("2");
        String c = new String("2");
        String d = new String("2");

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
        System.out.println(d.hashCode());
        System.out.println(c==d);
    }
}
