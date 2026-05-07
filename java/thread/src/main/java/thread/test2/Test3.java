package thread.test2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test3 {
    private final static Logger logger = LoggerFactory.getLogger(Test3.class);

    private Lock lock = new ReentrantLock();

    @Test
    public void test() {
        lock.lock();
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.unlock(); // 确保释放锁，从而避免发生死锁
        }
    }

    @Test
    public void test2() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotifyExample example = new WaitNotifyExample();
        executorService.execute(example::after);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        executorService.execute(example::before);
    }
}

class WaitNotifyExample {
    private static Logger logger = LoggerFactory.getLogger(WaitNotifyExample.class);

    public synchronized void before() {
        System.out.println("before");
        notifyAll(); // 唤醒所有的等待
    }

    public synchronized void after() {
        try {
            wait();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        System.out.println("after");
    }
}
