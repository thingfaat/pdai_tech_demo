package thread.test4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedObjectLock1 implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(SynchronizedObjectLock1.class);

    private final static SynchronizedObjectLock1 instance = new SynchronizedObjectLock1();

    // 创建2把锁
    private final static Object lock1 = new Object();
    private final static Object lock2 = new Object();

    @Override
    public void run() {
        // 这个代码块使用的是第一把锁，当他释放后，后面的代码由于使用的是第二把，因此可以马上执行
        synchronized (lock1) {
            logger.info("lock1锁，我是线程{}", Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("lock1锁，{}结束", Thread.currentThread().getName());
        }

        // 当先获取lock1锁的线程执行到这里的时候已经出了同步资源代码块了，
        // 所以另外一个就可以获取到lock1的同步资源锁了
        synchronized (lock2) {
            logger.info("lock2锁，我是线程{}", Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("lock2锁，{}结束", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
