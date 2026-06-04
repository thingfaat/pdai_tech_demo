package jvm.test6;

/**
 * String的不可变性
 */
public class StringTest1 {
    public static void main(String[] args) {
        String s1 = "abc"; // 字面量定义的方式，存储在字符串常量池中
        String s2 = "abc";
        System.out.println(s1 == s2); // true

        s1 = "hello";
        System.out.println(s1 == s2); // false 因为新分配了hello常量池位置的地址给s1，s1已经变化了

        System.out.println(s1); // hello
        System.out.println(s2); // abc
    }
}
