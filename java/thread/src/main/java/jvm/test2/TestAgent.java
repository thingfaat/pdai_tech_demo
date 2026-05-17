package jvm.test2;

import lombok.NonNull;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class TestAgent {
    public static void agentmain(String args, @NonNull Instrumentation inst) {
        // 指定我们自自定义的Transformer，在其中利用Javassist做字节码替换
        inst.addTransformer(new TestTransformer(), true);
        try {
            // 重定义类并载入新的字节码
            inst.retransformClasses(Hello1.class);
            System.out.println("Agent Load Done");
        } catch (UnmodifiableClassException e) {
            System.out.println("Agent Load Failed");
        }
    }
}
