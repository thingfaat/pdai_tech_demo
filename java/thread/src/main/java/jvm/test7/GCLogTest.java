package jvm.test7;

import java.util.ArrayList;

/**
 * 测试输出日志到log文件
 * -Xms60m -Xmx60m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -Xloggc:./logs/gc.log
 */
public class GCLogTest {
    public static void main(String[] args) {
        final var list = new ArrayList<byte[]>();

        for (var i = 0; i < 500; i++) {
            final var arr = new byte[1024 * 1024];
            list.add(arr);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
