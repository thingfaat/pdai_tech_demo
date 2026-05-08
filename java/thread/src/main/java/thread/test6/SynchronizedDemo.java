package thread.test6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedDemo {
    private final static Logger logger = LoggerFactory.getLogger(SynchronizedDemo.class);

    public static void main(String[] args) {
        SynchronizedDemo demo = new SynchronizedDemo();
        demo.method1();
    }

    private synchronized void method1() {
        logger.info("{}：method1()", Thread.currentThread().getId());
        method2();
    }

    private synchronized void method2() {
        logger.info("{}：method2()", Thread.currentThread().getId());
        method3();
    }

    private synchronized void method3() {
        logger.info("{}：method3()", Thread.currentThread().getId());
    }
}
