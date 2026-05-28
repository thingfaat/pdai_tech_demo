package jvm.test3;

public class LoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);

        // 使用 loadClass 加载不会执行静态块
        Class<?> clazz = loader.loadClass("jvm.test3.Test2");
        System.out.println(clazz);

        // 使用 Class.forName 加载初始化时默认执行静态块
        Class<?> clazz1 = Class.forName("jvm.test3.Test2");
        System.out.println(clazz1);

        // 使用 Class.forName 加载并指定 ClassLoader 初始化时不执行静态块
        Class<?> clazz2 = Class.forName("jvm.test3.Test2", false, loader);
        System.out.println(clazz2);
    }
}

class Test2 {
    static {
        System.out.println("静态初始化块执行了");
    }
}
