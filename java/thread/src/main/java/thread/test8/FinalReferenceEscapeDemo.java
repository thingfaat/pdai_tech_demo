package thread.test8;

public class FinalReferenceEscapeDemo {
    private final int a;
    private FinalReferenceEscapeDemo referenceEscapeDemo;

    public FinalReferenceEscapeDemo() {
        a = 1; // 1
        referenceEscapeDemo = this; // 2
    }

    public void writer() {
        new FinalReferenceEscapeDemo();
    }

    public void reader() {
        if (referenceEscapeDemo != null) { // 3
            int temp = referenceEscapeDemo.a; // 4
        }
    }
}