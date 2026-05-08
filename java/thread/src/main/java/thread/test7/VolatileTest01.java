package thread.test7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VolatileTest01 {
    private final static Logger logger = LoggerFactory.getLogger(VolatileTest01.class);

    volatile int i;

    public void addI() {
        i++; // volatile 字段 'i' 上的非原子操作，说明volatile只能保证单个操作的原子性，对于复杂的操作并不能保证并发的原子性
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest01 test01 = new VolatileTest01();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
                test01.addI();
            }).start();
        }
        Thread.sleep(10000);
        logger.info("i的值：{}", test01.i);
    }
}
