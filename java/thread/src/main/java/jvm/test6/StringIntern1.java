package jvm.test6;

public class StringIntern1 {
    public static void main(String[] args) {
        String s = new String("1");
        s.intern(); // 调用此方法时，字符串常量池中已经存在了”1“
        String s2 = "1";
        System.out.println(s == s2); // JDK17：false

        String s3 = new String("1") + new String("1"); // s3变量记录的地址为：new String("11")
        // 执行完上一行代码之后，字符串常量池中，不存在“11”
        s3.intern(); // 在字符串常量池中生成“11”，如何理解：JDK6中新创建了一个对象“11”，也就是有了新的地址
                                                    // JDK7中此时常量池中并没有创建“11”而是创建了一个指向堆空间的“11”的地址
        String s4 = "11"; // 使用的是上一行代码执行时在常量池中生成的“11”的地址
        System.out.println(s3 == s4); // JDK17：false
    }
}
