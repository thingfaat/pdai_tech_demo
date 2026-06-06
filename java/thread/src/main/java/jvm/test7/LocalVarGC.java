package jvm.test7;

import org.junit.Test;

public class LocalVarGC {
    @Test
    public void test1() {
        var bytes = new byte[1024];
        // 还存在强引用，无法正常回收
        System.gc();
    }

    @Test
    public void test2() {
        var bytes = new byte[1024];
        bytes = null;
        // 已被解除引用，可以正常回收
        System.gc();
    }

    @Test
    public void test3() {
        {
            var bytes = new byte[1024];
        }
        // bytes虽然离开了作用域，但是还占着局部变量表中槽1的位置，无法正常回收
        // JVM 的 GC 是否回收对象，不取决于变量作用域，而取决于该对象是否仍然被 GC Roots（包括栈帧局部变量表）引用；
        // 局部变量是否可达由 JIT 的活性分析决定，而不是 Java 语法块作用域。
        System.gc();
    }

    @Test
    public void test4() {
        {
            var bytes = new byte[1024];
        }
        int i = 0;
        // int i复用了槽1的位置，从而释放了bytes，使得内存可以正常回收
        System.gc();
    }
}
