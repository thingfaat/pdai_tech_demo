package bio;

import java.util.concurrent.CountDownLatch;

public class SocketClientDaemon {
    public static void main(String[] args) throws Exception {
        int clientNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(clientNumber);

        // 分别开始启动这20个客户端
        for (int index = 0; index < clientNumber; index++, countDownLatch.countDown()) {
            SocketClientRequestThread client = new SocketClientRequestThread(countDownLatch, index);
            new Thread(client).start();
        }

        synchronized (SocketClientDaemon.class) {
            SocketClientDaemon.class.wait();
        }
    }
}
