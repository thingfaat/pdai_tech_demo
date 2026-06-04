package jvm.test6;

import java.util.ArrayList;

/**
 * 测试MinorGC、MajorGC、FullGC
 * 需配置JVM参数：-Xms9m -Xmx9m -XX:+PrintGCDetails
 */
public class GCTest {

    public static void main(String[] args) {
        int i = 0;

        try {
            final var list = new ArrayList<String>();
            String a = "atguigu.com";
            while (true) {
                list.add(a);
                a = a + a;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("遍历次数为：" + i);
        }
    }
}
