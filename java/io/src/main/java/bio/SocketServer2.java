package bio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketServer2 {
    private static final Logger logger = LogManager.getLogger(SocketServer2.class);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(83)) {
            while (true) {
                Socket socket = serverSocket.accept();
                // 当然业务处理过程可以交给一个线程（这里可以使用线程池），并且线程的创建很消耗资源的
                // 最终改变了accept只能一个一个接受socket的情况，并且被阻塞的情况
                SocketserverThread socketserverThread = new SocketserverThread(socket);
                new Thread(socketserverThread).start();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

/**
 * 当然，接收到客户端的socket后，业务的处理过程可以交给一个线程来做，
 * 但还是改变不了socket被一个一个的做accept的情况
 */
class SocketserverThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(SocketserverThread.class);

    private final Socket socket;

    public SocketserverThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
            // 下面我们接收信息
            int sourcePort = socket.getPort();

            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            // 使用线程。同样无法解决read方法的阻塞问题
            // 也就是说read方法处同样也会阻塞，直到操作系统准备好
            int realLen = in.read(contextBytes, 0, maxLen);
            // 读取信息
            String message = new String(contextBytes, 0, realLen);
            logger.info("服务器收到来自于端口{}的信息{}", sourcePort, message);
            // 下面开始发送信息
            out.write("回发响应信息！".getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
