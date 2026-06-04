package jvm.test6;

public class DeadThreadTest {

    public static void main(String[] args) {
        final var runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始");
                final var deadThread = new DeadThread();
                System.out.println(Thread.currentThread().getName() + "结束");
            }
        };
        final var t1 = new Thread(runnable);
        final var t2 = new Thread(runnable);
        t1.start();
        t2.start();
    }
}

class DeadThread {
    // 类的初始化方法只会执行一次
    static {
        if (true) {
            System.out.println(Thread.currentThread().getName() + "初始化当前类");
            while (true) {
                // 死循环导致多线程初始化的情况下，一个初始化线程卡在这里，会导致其他线程无法执行类的初始化方法导致无法完成创建对象的动作
            }
        }
    }
}
