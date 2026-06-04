package jvm.test6;

import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * 查看直接内存的占用于释放
 */
public class BufferTest {

    private static final int BUFFER = 1024 * 1024 * 1024;

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER);
        System.out.println("直接内存申请完毕，请指示！");

        final var scanner = new Scanner(System.in);
        scanner.next();

        System.out.println("直接内存开始释放！");
        buffer = null;
        System.gc();
        scanner.next();
    }
}
