package thread.test3;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
    private final static Logger logger = LoggerFactory.getLogger(Test1.class);

    public synchronized void testMethod() {

    }

    private ReentrantLock lock = new ReentrantLock();

    public void modifyPublicResource() {
        lock.lock();
        try {
            // 操作同步资源
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Test
    public void test1() throws Exception {
        atomicInteger.incrementAndGet(); // 乐观锁操作同步资源
    }
}
