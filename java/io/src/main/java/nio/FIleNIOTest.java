package nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FIleNIOTest {

    @Test
    public void fastCopy() throws Exception {
        String src = "";
        String dest = "";
        // 获取源文件的输入字节流
        FileInputStream fin = new FileInputStream(src);
        // 获取输入字节流的文件通道
        FileChannel fcin = fin.getChannel();
        // 获取目标文件的输出字节流
        FileOutputStream fout = new FileOutputStream(dest);
        // 获取输出字节流的通道
        FileChannel fcout = fout.getChannel();
        // 为缓冲区非配1024个字节
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            // 从输入通道中读取数据奥缓冲区中
            int r = fcin.read(buffer);
            // read返回-1标识EOF
            if (r == -1) {
                break;
            }
            // 切换读写
            buffer.flip();
            // 把缓冲区的内容写入输出文件中
            fcout.write(buffer);
            // 清空缓冲区
            buffer.clear();
        }
    }
}
