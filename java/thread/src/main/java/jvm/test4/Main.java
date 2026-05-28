package jvm.test4;

public class Main {
    public static void main(String[] args) {
        // 返回 JVM 堆大小
        var initalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;

        // 返回 JVM 堆的最大内存
        var maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms : "+initalMemory + "M");
        System.out.println("-Xmx : "+maxMemory + "M");

        System.out.println("系统内存大小：" + initalMemory * 64 / 1024 + "G");
        System.out.println("系统内存大小：" + maxMemory * 4 / 1024 + "G");
    }
}
