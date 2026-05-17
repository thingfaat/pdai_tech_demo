package jvm.test2;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class JavassistDemo {
    public static void main(String[] args) throws Exception {
        // 获取ClassPool
        ClassPool pool = ClassPool.getDefault();

        // 获取要修改的类
        CtClass cc = pool.getCtClass("jvm.test2.Hello1");

        // 获取say方法
        CtMethod method = cc.getDeclaredMethod("say", new CtClass[]{pool.get("java.lang.String")});
        method.insertBefore("System.out.println(\"[Before] say(\" + $1 + \")\");");
        // 在方法返回前插入代码
        method.insertAfter("System.out.println(\"[After] say\");", true);  // true 表示在 finally 中执行

        // 将修改后的类写入文件（或直接加载）
        // 使用 toClass(Class) 传 lookup class，避免 JDK 17 模块系统限制
        Class<?> clazz = cc.toClass(JavassistDemo.class);
        Object obj = clazz.getDeclaredConstructor().newInstance();
        obj.getClass().getMethod("say", String.class).invoke(obj, "Javassist");
    }
}
