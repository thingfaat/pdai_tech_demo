package jvm.test6;

/**
 * 栈上逃逸分析测试
 * 需要添加VM参数：-Xms1G -Xmx1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails，通过+/-开启或关闭栈上分配
 */
public class StackAllocation {
    public static void main(String[] args) {
        final var start = System.currentTimeMillis();

        for (var i = 0; i < 10_000_000; i++) {
            allocate();
        }

        // 查看执行时间
        final var end = System.currentTimeMillis();
        System.out.println("花费的时间：" + (end - start) + "ms");

        // 为了方便查看堆内存中对象的个数据，线程Sleep
        try {
            Thread.sleep(10_000_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void allocate() {
        final var user = new User();
    }

    static class User {

    }
}
