package thread.test2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test2 {
    private final static Logger logger = LoggerFactory.getLogger(Test2.class);

    public void func() {
        synchronized (this) { // 同步的对象
            // 同步内容
        }
    }

    public synchronized void func2() { // 同步的是对象

    }

    public synchronized static void func3() { // 同步的是类Class字节码文件

    }

    public void func4() {
        synchronized (Test2.class) { // 同步的当前类，因为字节码文件容器中只有一个，所以是整个JVM都是同步的

        }
    }

    @Test
    public void test() throws Exception {
        new Test2().func(); // 创建两个，是两个对象上的内容块，就不会同步
        new Test2().func();
    }
}
