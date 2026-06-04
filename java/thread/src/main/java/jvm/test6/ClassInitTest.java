package jvm.test6;

public class ClassInitTest {
    static {
        num = 2;
    }

    private static int num = 1;

    public static void main(String[] args) {
        System.out.println(num); // 1
    }
}
