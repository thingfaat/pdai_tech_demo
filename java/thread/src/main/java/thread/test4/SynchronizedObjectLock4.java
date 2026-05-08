package thread.test4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedObjectLock4 implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(SynchronizedObjectLock4.class);

    private final static SynchronizedObjectLock4 instance1 = new SynchronizedObjectLock4();
    private final static SynchronizedObjectLock4 instance2 = new SynchronizedObjectLock4();

    @Override
    public void run() {
        method();
    }

    /**
     * synchronized用在静态方法上，默认的锁就是当前所在Class类，
     * 所以无论是哪个线程访问它，需要的锁都是同一把
     */
    private static synchronized void method() {
        logger.info("我是线程{}", Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("{}线程结束", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
