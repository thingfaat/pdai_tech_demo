package thread.test4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedObjectLock implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(SynchronizedObjectLock.class);

    private final static SynchronizedObjectLock instance = new SynchronizedObjectLock();

    @Override
    public void run() {
        // 同步代码块形式：锁的是this，两个线程使用的锁是一样的，线程1必须要等到线程0释放了该锁后才能执行
        synchronized (this) {
            logger.info("我是线程{}", Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("{}结束", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        // 因为传入的是同一个对象 instance 所以sync中锁的对象是同一个
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();

        // 如果传入的对象不是同一个的话，无法实现锁同步资源的效果
        // SynchronizedObjectLock lock1 = new SynchronizedObjectLock();
        // SynchronizedObjectLock lock2 = new SynchronizedObjectLock();
        // new Thread(lock1).start();
        // new Thread(lock2).start();
    }
}
