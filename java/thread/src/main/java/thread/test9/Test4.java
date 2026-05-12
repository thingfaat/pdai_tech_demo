package thread.test9;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Test4 {
    private final static Lock lock = new ReentrantLock();
    private final static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        MyThread2 myThread1 = new MyThread2();
        myThread1.start();

        lock.lock();
        try {
            System.out.println("before park");
            condition.await();
            System.out.println("after park");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("before signal");
                condition.signal();
                System.out.println("after signal");
            } finally {
                lock.unlock();
            }
        }
    }
}

