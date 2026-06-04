package jvm.test6;

public class Memory {
    public static void main(String[] args) {
        int i = 1;
        Object obj = new Object();
        Memory mem = new Memory();
        mem.foo(obj);
    }

    private void foo(final Object parm) {
        String str = parm.toString();
        System.out.println(str);
    }
}
