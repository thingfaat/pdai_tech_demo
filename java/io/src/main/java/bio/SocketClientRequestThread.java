package bio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * 一个SocketClientRequestThread线程模拟一个客户端请求
 */
public class SocketClientRequestThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(SocketClientRequestThread.class);

    private final CountDownLatch countDownLatch;

    /**
     * 这个线程的编号
     */
    private final Integer clientIndex;

    public SocketClientRequestThread(CountDownLatch countDownLatch, Integer clientIndex) {
        this.countDownLatch = countDownLatch;
        this.clientIndex = clientIndex;
    }

    @Override
    public void run() {
        Socket socket = null;
        OutputStream clientRequest = null;
        InputStream clientResponse = null;

        try {
            socket = new Socket("localhost", 83);
            clientRequest = socket.getOutputStream();
            clientResponse = socket.getInputStream();

            // 等待，直到SocketClientDaemon完成所有线程的启动，然后所有线程一起发送
            this.countDownLatch.await();

            // 发送请求信息
            clientRequest.write(String.format("这是第%d个客户端的请求发送成功，等待服务器返回信息", clientIndex).getBytes());
            clientRequest.flush();

            // 在这里等待，直到服务器返回信息
            logger.info("第{}个客户端的请求发送完成，等待服务器返回信息", this.clientIndex);
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            int readLen;
            StringBuilder message = new StringBuilder();
            // 程序执行到这里，会一直等待服务器返回信息（注意：前提是in和out都不能close，如果close了就不收不到服务器的反馈了）
            while ((readLen = clientResponse.read(contextBytes, 0, maxLen)) != -1) {
                message.append(new String(contextBytes, 0, readLen));
            }
            logger.info("接收到来自服务器的信息：{}", message.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (clientRequest != null) {
                    clientRequest.close();
                }
                if (clientResponse != null) {
                    clientResponse.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
