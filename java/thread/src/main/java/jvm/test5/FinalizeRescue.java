package jvm.test5;

public class FinalizeRescue {
    private static FinalizeRescue SAVED_REF = null; // 用来自救的静态引用

    private String name;

    public FinalizeRescue(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(name + " 执行 finalize()，尝试自救...");
        // 自救：把自己赋给静态变量
        SAVED_REF = this;
        System.out.println(name + " 自救完成，重新被引用！");
    }

    public void hello() {
        System.out.println("你好，我是 " + name);
    }

    public static void main(String[] args) throws InterruptedException {
        // 1. 创建对象，然后切断外部引用（变成可回收）
        FinalizeRescue obj = new FinalizeRescue("对象A");
        obj.hello();                      // 正常使用
        obj = null;                      // 失去引用，成为“可回收”

        // 2. 手动触发垃圾回收（仅建议，不保证立刻执行）
        System.gc();
        // 等待一段时间让 GC 和 finalize 线程工作
        Thread.sleep(1000);

        // 3. 检查是否自救成功
        if (SAVED_REF != null) {
            System.out.println("自救成功，对象仍存活！");
            SAVED_REF.hello();
        } else {
            System.out.println("自救失败，对象已被回收。");
        }

        // ========== 第二次尝试回收同一个对象 ==========
        System.out.println("\n--- 第二次让对象失去引用 ---");
        SAVED_REF = null;               // 切断上一次自救的引用
        System.gc();
        Thread.sleep(1000);

        if (SAVED_REF == null) {
            System.out.println("第二次不再调用 finalize，对象直接被回收。");
        }
    }
}
