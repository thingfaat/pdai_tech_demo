package jvm.test6;

/**
 * StringTable的垃圾回收
 * -Xms15m -Xmx15m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails
 */
public class StringGCTest {
    public static void main(String[] args) {
        for (var i = 0; i < 100; i++) {
            String.valueOf(i).intern();
        }
    }
}
