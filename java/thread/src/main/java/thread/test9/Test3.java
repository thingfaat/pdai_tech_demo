package thread.test9;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Test3 {
    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        MyThread2 myThread1 = new MyThread2(Thread.currentThread());
        myThread1.start();

        lock.lock();
        try {
            System.out.println("before park");
            LockSupport.park("Test3"); // 由于不会让出锁，导致其他线程如果在获取锁的时候才unpark，那么永远不会被唤醒
            System.out.println("after park");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static class MyThread2 extends Thread {
        private Object object;

        public MyThread2(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("before unpark");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("blocker info " + LockSupport.getBlocker((Thread) object));
                LockSupport.unpark((Thread) object);
                // 休眠500ms 保证先执行park中的setBlocker
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("blocker info " + LockSupport.getBlocker((Thread) object));
                System.out.println("after unpark");
            } finally {
                lock.unlock();
            }
        }
    }
}

