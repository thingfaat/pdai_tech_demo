package thread.test4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedObjectLock5 implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(SynchronizedObjectLock5.class);

    private final static SynchronizedObjectLock5 instance1 = new SynchronizedObjectLock5();
    private final static SynchronizedObjectLock5 instance2 = new SynchronizedObjectLock5();

    @Override
    public void run() {
        // 所有的线程需要的锁都是同一把
        synchronized (SynchronizedObjectLock5.class) {
            logger.info("我是线程{}", Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("{}线程结束", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
