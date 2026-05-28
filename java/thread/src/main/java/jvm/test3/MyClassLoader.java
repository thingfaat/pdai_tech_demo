package jvm.test3;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
@Getter
@Setter
public class MyClassLoader extends ClassLoader {
    private String root;

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException(name);
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] loadClassData(String className) throws ClassNotFoundException {
        String fileName = root + File.separatorChar + className.replace('.', File.separatorChar) + ".class";

        try (InputStream ins = new FileInputStream(fileName); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static void main(String[] args) {
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setRoot(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath());

        try {
            Class<?> clazz = classLoader.loadClass("jvm.test3.Test3");
            System.out.println(clazz);
            Test3 instance = (Test3) clazz.newInstance();
            System.out.println(instance);
            instance.hello();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
