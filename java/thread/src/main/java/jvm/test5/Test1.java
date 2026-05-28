package jvm.test5;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

@Slf4j
public class Test1 {

    @Test
    public void test1() {
        // 强引用
        Object obj = new Object();

        // 软引用
        Object obj1 = new Object();
        WeakReference<Object> sf = new WeakReference<>(obj1);
        obj1 = null; // 使对象只被软引用关联

        // 弱引用
        Object obj2 = new Object();
        WeakReference<Object> wf = new WeakReference<>(obj2);
        obj2 = null;

        Object obj4 = new Object();
        PhantomReference<Object> pf = new PhantomReference<>(obj4, new ReferenceQueue<>());
        obj4 = null;
    }
}
