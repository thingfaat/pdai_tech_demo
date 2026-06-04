package jvm.test6;

public class Test1 {

    public void test1() {
        // 方法1：在方法内部创建的局部变量，且在内部消亡的，是线程安全的
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        System.out.println(sb.toString());
    }

    // 方法2：通过外部传入的局部变量是线程不安全的
    public void test2(StringBuilder sb) {
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        System.out.println(sb.toString());
    }

    // 方法3：将局部变量作为参数返回，是线程不安全的
    public StringBuilder test3() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        return sb;
    }

    // 方法4：将局部变量转为不可变对象返回，方法内部的局部变量随着栈帧的弹出消亡，是线程安全的
    public String test4() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        return sb.toString();
    }
}
