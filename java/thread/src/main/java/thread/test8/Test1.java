package thread.test8;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test1 {
    private final static Logger logger = LoggerFactory.getLogger(Test1.class);

    @Test
    public void test1() {
        final byte a = 125; // 编译期常量
        final byte b = 126; // 编译期常量
        // byte c = a + b; // 这行出现报错，编译期识别超出范围

        final byte a1 = 1;
        final byte b1 = 2;
        final byte c1 = a1 + b1; // 不会报错，因为编译期能识别出未超出byte范围

        byte a2 = 1; // 非编译期常量，编译器无法识别
        byte b2 = 2; // 非编译期常量
        // byte c2 = a2 + b2; // 报错，自动升级为int类型导致无法赋值给byte类型
    }
}
