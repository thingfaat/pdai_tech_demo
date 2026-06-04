package jvm.test6;

import org.junit.Test;

public class StringTest5 {
    @Test
    public void test1() {
        String s1 = "a" + "b" + "c";
        String s2 = "abc";

        /*
         * 编译期优化：最终.java文件编译成.class，再执行.class
         * String s1 = "abc";
         * String s2 = "abc";
         */
        System.out.println(s1 == s2); // 地址比较，都是指向常量池中的一个地址 true
        System.out.println(s1.equals(s2)); // 内容比较 true
    }

    @Test
    public void test2() {
        String s1 = "javaEE";
        String s2 = "hadoop";

        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop"; // 编译期优化
        // 如果拼接符号前后出现变量，则相当于在堆空间中new StringBuilder()，使用append拼接
        String s5 = s1 + "hadoop";
        String s6 = "javaEE" + s2;
        String s7 = s1 + s2;

        System.out.println(s3 == s4); // true
        System.out.println(s3 == s5); // false
        System.out.println(s3 == s6); // false
        System.out.println(s3 == s7); // false
        System.out.println(s5 == s6); // false
        System.out.println(s5 == s7); // false
        System.out.println(s6 == s7); // false

        // intern 判断字符串常量池中是否存在javaEEhadoop值，如果存在，则返回常量池中JavaEEhadoop的地址，
        // 如果字符串常量池中不存在javaEEhadoop，则在常量池中加载一份JavaEEhadoop，并返回此对象的地址。
        String s8 = s6.intern();
        System.out.println(s3 == s8); // true
    }

    @Test
    public void test3() {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        /*
         * 之前的版本是使用StringBuilder的append实现的拼接方法，类似于：
         * StringBuilder sb = new StringBuilder();
         * sb.append("a");
         * sb.append("b");
         * s.toString()：相当于new String("ab")
         * JDK9开始引入makeConcatWithConstants实现，
         * 根据拼接表达式中的常量参数（如字面量）和动态参数（变量），在运行时生成一个最优的拼接策略（例如直接分配一个 byte[] 数组并填充内容，
         * 或者组合使用 StringBuilder 等）
         */
        String s4 = s1 + s2;
        System.out.println(s3 == s4); // false
    }

    /**
     * 字符串拼接操作不一定使用的StringBuilder，如果拼接符号左右两边都是字符串常量或者常量引用，则仍然使用编译期优化
     */
    @Test
    public void test4() {
        final String s1 = "a";
        final String s2 = "b";
        String s3 = "ab";
        String s4 = s1 + s2;
        System.out.println(s3 == s4); // true
    }
}
