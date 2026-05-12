package thread.test8;

public class FinalReferenceDemo {
    final int[] arrays;
    private FinalReferenceDemo finalReferenceDemo;

    public FinalReferenceDemo() {
        this.arrays = new int[1]; // 1
        arrays[0] = 1; // 2
    }

    public void writeOne() {
        finalReferenceDemo = new FinalReferenceDemo(); // 3
    }

    public void writeTwo() {
        arrays[0] = 2; // 4
    }

    public void reader() {
        if (finalReferenceDemo != null) { // 5
            int temp = finalReferenceDemo.arrays[0]; // 6
        }
    }
}
