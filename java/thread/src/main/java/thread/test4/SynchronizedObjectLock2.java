package thread.test4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedObjectLock2 implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(SynchronizedObjectLock2.class);

    private final static SynchronizedObjectLock2 instance = new SynchronizedObjectLock2();

    @Override
    public void run() {
        method();
    }

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
        // 因为所有线程都是用的同一个实例对象，锁的对象是同一个
        // 所以代码是顺序执行输出的
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
