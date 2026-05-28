package jvm.test3;

public class Test3 {
    private static String name = "张三";

    static {
        name = "李四";
        System.out.println("静态初始化块执行了，name=" + name);
    }

    public void hello() {
        System.out.println("hello " + name);
    }
}
