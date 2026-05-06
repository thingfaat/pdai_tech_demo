package reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Acceptor implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(Acceptor.class.getName());

    private final ExecutorService executor = Executors.newFixedThreadPool(20);
    private final ServerSocketChannel serverSocket;

    public Acceptor(ServerSocketChannel serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            SocketChannel channel = serverSocket.accept(); // 获取客户端连接
            if (null != channel) {
                executor.execute(new Handler(channel)); // 将客户端连接交给线程池处理
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
