package selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class SelectorServerTest {
    private final static int PORT = 8888;
    public static final int CAPACITY = 1024;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false); // 非阻塞
        serverChannel.socket().bind(new InetSocketAddress(PORT));
        Selector selector = Selector.open();
        // 将serverChannel的连接事件绑定到selector上，告诉selecor说每天就检查客户端有没有连接就绪的
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int n = selector.select();
            if (n == 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) { // 事件类型是连接
                    // 这里的ServerSocketChannel就是来着客户端一对一的连接了，不是上面的总的监听端口的那个了
                    ServerSocketChannel clientServerChannel = (ServerSocketChannel) key.channel();
                    // 还是一样，给selector添加任务，在帮我每天看看这些来自客户端的连接的是否可读
                    clientServerChannel.configureBlocking(false); // 非阻塞
                    clientServerChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(CAPACITY));
                } else if (key.isReadable()) { // 事件类型可读
                    handleRead(key);
                } else if (key.isWritable() && key.isValid()) {
                    handleWrite(key);
                }
                iterator.remove();
            }
        }
    }

    private static void handleWrite(SelectionKey key) {
        // TODO 处理从服务端写回客户端内容
    }

    private static void handleRead(SelectionKey key) {
        // TODO 处理客户端发送服务端的内容
    }
}
