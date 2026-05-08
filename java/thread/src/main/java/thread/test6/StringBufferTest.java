package thread.test6;

public class StringBufferTest {
    private static String strTest(String s1, String s2) {
        String s = s1 + s2;
        return s;
    }

    public static void main(String[] args) {
        strTest("1", "2");
    }
}
