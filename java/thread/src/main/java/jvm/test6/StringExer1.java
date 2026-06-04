package jvm.test6;

public class StringExer1 {
    public static void main(String[] args) {
        String s = new String("a") + new String("b"); // new String("ab")
        // 此时常量池中并没有“ab”
        String s2 = s.intern(); // jdk6中，在串池中创建“ab”对象并把结果赋值给s2
                                // jdk8中，在串池中没有创建“ab”对象，而是创建一个引用指向上面new出来的“ab”地址，将引用返回
        System.out.println(s2 == "ab"); // true
        System.out.println(s == "ab"); // true
    }
}
