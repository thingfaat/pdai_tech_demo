package thread.test7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestVolatile {
    private final static Logger logger = LoggerFactory.getLogger(TestVolatile.class);

    private volatile static boolean stop = false;

    public static void main(String[] args) {
        new Thread("Thread A") {
            @Override
            public void run() {
                // thread a 会一直卡在这里，因为主线程对stop的修改，对于thread a是不可见的
                // 如果在stop上添加volatile则会跳出循环正常结束
                while (!stop) {
                }
                logger.info("{} stopped", Thread.currentThread().getName());
            }
        }.start();

        try {
            TimeUnit.SECONDS.sleep(1);
            logger.info("{} after 1 seconds", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            logger.error("{} interrupted", Thread.currentThread().getName());
        }
        stop = true;
    }
}
