package jvm.test6;

public class StringExpr2 {
    public static void main(String[] args) {
        String s1 = new String("ab"); // 常量池中已经有了“ab”对象，下面的比较输出false
        // String s1 = new String("a") + new String("b"); // 池中还没有，所以能放进去，下面的比较输出是true
        s1.intern(); // 想要放进去，已经存在就直接返回已经存在的“ab”对象的地址
        String s2 = "ab"; // 串池中ab对象的地址
        System.out.println(s1 == s2);
    }
}
