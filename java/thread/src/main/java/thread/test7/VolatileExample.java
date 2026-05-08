package thread.test7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VolatileExample {
    private final static Logger logger = LoggerFactory.getLogger(VolatileExample.class);

    int a = 0;
    volatile boolean flag = false;

    public void writer() {
        a = 1; // 1 线程A修改共享变量
        flag = true; // 2 线程A写volatile变量
    }

    public void reader() {
        if (flag) { // 3 线程B读取同一个volatile变量
            int i = a; // 4 线程B读取共享变量
            logger.info("i = {}", i);
        }
    }
}
