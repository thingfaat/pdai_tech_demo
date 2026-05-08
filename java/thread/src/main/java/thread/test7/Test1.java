package thread.test7;

public class Test1 {
    private volatile int a;

    public void update() {
        a = 1;
    }

    public static void main(String[] args) {
        Test1 test = new Test1();
        test.update();
    }
}
