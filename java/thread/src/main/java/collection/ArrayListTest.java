package collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;

@Slf4j
public class ArrayListTest {
    @Test
    public void test() {
        ArrayList list = new ArrayList();
        list.add(1);
        list.add(2);
        list.ensureCapacity(12);
        list.add(3);
        log.info("list size = {}", list.size());
    }
}
