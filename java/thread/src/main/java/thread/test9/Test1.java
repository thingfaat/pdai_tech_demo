package thread.test9;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class Test1 {
    private final static Logger logger = LoggerFactory.getLogger(Test1.class);

    @Test
    public void test1() {
        AtomicIntegerArray array = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});
        logger.info(array.toString());
        logger.info("{}", array.getAndAdd(1, 2));
        logger.info(array.toString());
    }

    @Test
    public void test2() {
        // 创建两个Person对象
        Person p1 = new Person("张三");
        Person p2 = new Person("李四");

        AtomicReference<Person> ar = new AtomicReference<>(p1);
        ar.compareAndSet(p1, p2);

        Person p3 = ar.get();
        logger.info("p3 = {}", p3);
        logger.info("p3.equals(p1): {}", p3.equals(p1));
    }
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}
