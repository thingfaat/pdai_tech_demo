package bio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketServer1 {
    private static final Logger logger = LogManager.getLogger(SocketClientRequestThread.class);

    public static void main(String[] args) {
        int port = 83;
        logger.info("开始服务端监听端口{}", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                // 下面我们收取信息
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                int sourcePort = socket.getPort();
                int maxLen = 2048;
                byte[] contextBytes = new byte[maxLen];
                // 这里也会阻塞，直到数据准备好
                int realLen = in.read(contextBytes, 0, maxLen);
                // 读取信息
                String content = new String(contextBytes, 0, realLen);
                logger.info("服务器收到来着与端口{}的信息：{}", sourcePort, content);
                // 下面开始发送信息
                out.write("回发响应信息！".getBytes(StandardCharsets.UTF_8));
                // 关闭
                out.close();
                in.close();
                socket.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
