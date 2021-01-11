package cn.com.dmg.interviews2.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@Data
@AllArgsConstructor
@ToString
class User{
    String name;
    int age;
}

public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User z3 = new User("z3", 22);
        User l4 = new User("l4", 25);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.toString());
        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.toString());
    }
}

