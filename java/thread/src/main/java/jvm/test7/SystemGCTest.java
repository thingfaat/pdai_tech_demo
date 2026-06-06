package jvm.test7;

public class SystemGCTest {
    public static void main(String[] args) {
        new SystemGCTest();
        System.gc(); // 提醒JVM的垃圾回收器执行gc，但是不是马上执行gc

        // System.runFinalization(); // 会强制调用失去引用的对象的finalize方法
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("SystemGCTest 重写了 finalize");
    }
}
