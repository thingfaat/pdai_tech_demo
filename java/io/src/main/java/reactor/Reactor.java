package reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(Reactor.class);

    private final Selector selector;
    private final ServerSocketChannel serverSocket;

    public Reactor(int port) throws IOException {
        this.serverSocket = ServerSocketChannel.open(); // 创建服务端的ServerSocketChannel
        this.serverSocket.configureBlocking(false); // 设置为非阻塞
        this.selector = Selector.open(); // 创建一个Selector多路复用
        SelectionKey key = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        serverSocket.bind(new InetSocketAddress(port)); // 绑定服务端端口
        key.attach(new Acceptor(serverSocket));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select(); // 服务端使用一个线程不断等待客户端的连接到达
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    dispatch(iterator.next()); // 监听到客户端的连接事件后将其分发给Acceptor
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void dispatch(SelectionKey key) {
        // 这里的attachment也即前面服务端Channel绑定Acceptor，调用其run方法进行
        // 客户端连接的获取，并且进行分发
        Runnable attachment = (Runnable) key.attachment();
        attachment.run();
    }

    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(8888);
        reactor.run();
    }
}
