package thread.test6;

import org.junit.Test;

import java.util.logging.Logger;

public class StringTest {
    private final static Logger logger = Logger.getLogger(StringTest.class.getName());

    /**
     * append本质上就是锁粗化的实例，对Stringbuffer的操作是需要加锁的，
     * 但是不能说每次append都需要加锁，其实可以
     */
    @Test
    public void test1() {
        StringBuffer sb = new StringBuffer();
        sb.append(1);
        sb.append(2);
        sb.append(3);
        logger.info(sb.toString());
    }
}
