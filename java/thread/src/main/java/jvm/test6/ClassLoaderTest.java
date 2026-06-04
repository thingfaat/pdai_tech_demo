package jvm.test6;

public class ClassLoaderTest {
    public static void main(String[] args) {

        // 获取系统类加载器
        final var systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader); // jdk.internal.loader.ClassLoaders$AppClassLoader@251a69d7

        // 获取上层：扩展类加载器
        final var extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader); // jdk.internal.loader.ClassLoaders$PlatformClassLoader@3f91beef

        // 获取上层：获取不到引导类加载器
        final var boostrapClassLoader = extClassLoader.getParent();
        System.out.println(boostrapClassLoader); // null

        // 对于用户自定义类来说：默认使用系统类加载器来加载
        final var classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader); // jdk.internal.loader.ClassLoaders$AppClassLoader@251a69d7

        //  String 类使用引导类加载器进行加载的，Java的核心类库都是用引导类加载器进行加载的
        // 核心类主要是指rt.jar
        final var classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1); // null
    }
}
