package jvm.test6;

/**
 * 测试解释器模式和JIT模式
 * -Xint 花费的时间：8690ms
 * -Xcomp 花费的时间：302ms
 * -Xmixed 花费的时间：346ms
 */
public class IntCompTest {
    public static void main(String[] args) {
        final var start = System.currentTimeMillis();

        testPrimeNumber(100_0000);

        final var end = System.currentTimeMillis();
        System.out.println("花费的时间：" + (end - start) + "ms");
    }

    private static void testPrimeNumber(final int count) {
        for (var i = 0; i < count; i++) {
            // 计算100以内的质数
            label:
            for (int j = 2; j <= 100; j++) {
                for (int k = 2; k < Math.sqrt(j); k++) {
                    if (j * k == i) {
                        continue label;
                    }
                }
            }
        }
    }
}
