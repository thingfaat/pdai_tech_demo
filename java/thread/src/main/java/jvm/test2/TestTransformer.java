package jvm.test2;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

@Slf4j
public class TestTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            // 获取ClassPool
            ClassPool pool = ClassPool.getDefault();

            // 获取要修改的类
            CtClass cc = pool.getCtClass("jvm.test2.Hello1");

            // 获取say方法
            CtMethod method = cc.getDeclaredMethod("say", new CtClass[]{pool.get("java.lang.String")});
            method.insertBefore("System.out.println(\"[Before] say(\" + $1 + \")\");");
            // 在方法返回前插入代码
            method.insertAfter("System.out.println(\"[After] say\");", true);  // true 表示在 finally 中执行

            return cc.toBytecode();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
