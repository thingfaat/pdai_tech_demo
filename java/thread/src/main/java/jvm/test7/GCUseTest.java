package jvm.test7;

import java.util.ArrayList;

/**
 * 测试GC垃圾回收器相关命令行参数，需要添加VM命令号参数
 * -XX:+PrintCommandLineFlags
 */
public class GCUseTest {
    public static void main(String[] args) {
        final var bytes = new ArrayList<byte[]>();

        while (true) {
            final var arr = new byte[100];
            bytes.add(arr);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
