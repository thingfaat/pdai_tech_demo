package jvm.test6;

/**
 * staticObj、instanceObj|localObj存放在哪里？
 */
public class StaticObjTest {

    static class Test {
        static ObjectHolder staticObj = new ObjectHolder();

        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            final var localObj = new ObjectHolder();
            System.out.println("done");
        }
    }

    private static class ObjectHolder {

    }

    public static void main(String[] args) {
        final var test = new Test();
        test.foo();
    }
}
