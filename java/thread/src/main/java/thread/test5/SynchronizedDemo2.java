package thread.test5;

public class SynchronizedDemo2 {
    Object object = new Object();

    public void method1() {
        synchronized (object) {
            // todo
        }
        method2();
    }

    private synchronized void method2() {
        // todo
    }
}
