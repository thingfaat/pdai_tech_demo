package thread.test4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedObjectLock3 implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(SynchronizedObjectLock3.class);

    private final static SynchronizedObjectLock3 instance1 = new SynchronizedObjectLock3();
    private final static SynchronizedObjectLock3 instance2 = new SynchronizedObjectLock3();

    @Override
    public void run() {
        method();
    }

    /**
     * synchronized用在普通方法上，默认的锁就是this，当前实例
     */
    private synchronized void method() {
        logger.info("我是线程{}", Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("{}线程结束", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        // 因为传入的实例不一样，所以锁同步资源的是无效的
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
