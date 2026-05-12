package thread.test9;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class TestAtomicIntegerFieldUpdater {

    public static void main(String[] args) {
        TestAtomicIntegerFieldUpdater tia = new TestAtomicIntegerFieldUpdater();
        tia.doIt();
    }

    public AtomicIntegerFieldUpdater<DataDemo> updater(String name) {
        return AtomicIntegerFieldUpdater.newUpdater(DataDemo.class, name);
    }

    private void doIt() {
        DataDemo data = new DataDemo();
        System.out.printf("publicVar=%d%n", updater("publicVar").getAndAdd(data, 2));
    }
}

class DataDemo {
    public volatile int publicVar = 3;
    protected volatile int protectedVar = 4;
    private volatile int privateVar = 5;

    public volatile static int staticVar = 6;

    public volatile Integer integerVar = 19;
    public volatile Long longVar = 18L;
}
