package jvm.test2;

import org.objectweb.asm.*;

import java.io.InputStream;

public class AsmDemo1 {
    public static void main(String[] args) throws Exception {
        // 读取 Hello.class 字节码（与当前类同目录）
        try (InputStream is = AsmDemo1.class.getResourceAsStream("Hello.class")) {
            byte[] original = is.readAllBytes();
            ClassReader cr = new ClassReader(original);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                                 String[] exceptions) { // 方法读取到的时候被触发
                    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                    if (name.equals("say") && desc.equals("(Ljava/lang/String;)V")) { // 方法名字如果是say的
                        // 返回一个包装的 MethodVisitor，覆写 visitCode 来插入前置，覆写 visitInsn(RETURN) 插入后置
                        return new MethodVisitor(Opcodes.ASM9, mv) {
                            /**
                             * 前置切面
                             */
                            @Override
                            public void visitCode() {
                                // 插入前置指令
                                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                                mv.visitLdcInsn("Before method");
                                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                                        "(Ljava/lang/String;)V", false);
                                super.visitCode();
                            }

                            /**
                             * 后置切面
                             */
                            @Override
                            public void visitInsn(int opcode) {
                                if (opcode == Opcodes.RETURN) {
                                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
                                            "Ljava/io/PrintStream;");
                                    mv.visitLdcInsn("After method");
                                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                                            "(Ljava/lang/String;)V", false);
                                }
                                super.visitInsn(opcode);
                            }
                        };
                    }
                    return mv;
                }
            };

            cr.accept(cv, 0);
            byte[] enhanced = cw.toByteArray();

            // 动态加载并调用增强后的类
            var loader = new ClassLoader() {
                public Class<?> define(String name, byte[] b, int off, int len) {
                    return super.defineClass(name, b, off, len);
                }
            };
            Class<?> clazz = loader.define("jvm.test2.Hello", enhanced, 0, enhanced.length);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("say", String.class).invoke(obj, "ASM");
        }
    }
}