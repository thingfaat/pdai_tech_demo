package jvm.test5;

import lombok.val;

public class ReferenceCountingGC {
    public Object instance = null;

    public static void main(String[] args) {
        val objectA = new ReferenceCountingGC();
        val objectB = new ReferenceCountingGC();
        objectA.instance = objectB;
        objectB.instance = objectA;
    }
}
