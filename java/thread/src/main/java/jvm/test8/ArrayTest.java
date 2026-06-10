package jvm.test8;

public class ArrayTest {
    public static void main(String[] args) {
        final var arr = new Object[10];
        System.out.println(arr); // [Ljava.lang.Object;@30f39991

        final var arr1 = new String[10];
        System.out.println(arr1); // [Ljava.lang.String;@452b3a41

        final var arr2 = new long[10][];
        System.out.println(arr2); // [[J@4a574795
    }
}
