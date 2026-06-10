package jvm.test8;

import org.junit.Test;

public class ArithmeticTest {
    @Test
    public void test1() {
        int i = 10;
        double j = i / 0.0;
        System.out.println(j); // Infinity 无穷大

        double d1 = 0.0;
        double d2 = d1 / 0.0;
        System.out.println(d2); // NaN not a number
    }
}
