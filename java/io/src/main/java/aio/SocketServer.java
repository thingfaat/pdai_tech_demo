package aio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private final static Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private static final Object waitObject = new Object();

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);
        AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open(group);

        // 设置要监听的端口“0.0.0.0”代表本机所有IP设备
        serverSocket.bind(new InetSocketAddress("0.0.0.0", 83));
        // 为AsynchronousServerSocketChannel注册监听，注意只是为AsynchronousServerSocketChannel通道注册监听
        // 并不包括为 随后客户端和服务器 socket channel通道注册的监听
        serverSocket.accept(null, new ServerSocketChannelHandle(serverSocket));

        // 等待，以便观察现象(这个和要讲解的原理本身没有任何关系，只是为了保证守护线程不会退出)
        synchronized (waitObject) {
            waitObject.wait();
        }
    }
}

/**
 * 这个处理器类，专门用来响应ServerSocketChannel的事件
 */
class ServerSocketChannelHandle implements CompletionHandler<AsynchronousSocketChannel, Void> {
    private final static Logger logger = LoggerFactory.getLogger(ServerSocketChannelHandle.class);

    private AsynchronousServerSocketChannel serverSocketChannel;

    /**
     * @param serverSocketChannel
     */
    public ServerSocketChannelHandle(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    /**
     * 注意，我们分别观察 this socketChannel attachment 三个对象的id
     *
     * @param socketChannel The result of the I/O operation.
     * @param attachment    The object attached to the I/O operation when it was initiated.
     */
    @Override
    public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
        logger.info("completed(AsynchronousSocketChannel result, ByteBuffer attachment)");
        // 每次都要重新注册监听（一次注册，一次响应）但是由于文件标识符是独享的，所以不需要担心又漏掉的事件
        this.serverSocketChannel.accept(attachment, this);

        // 为这个新的socketChannel注册“read”事件，以便操作系统在收到数据并准备好后，主动通知应用程序
        // 在这里，由于我们要将这个客户端多次传输的数据累加起来一起处理，所以我们将一个stringbuffer对象作为一个“附件”依附在这个channel上
        ByteBuffer readBuffer = ByteBuffer.allocate(50);
        socketChannel.read(readBuffer, new StringBuffer(), new SocketChannelReadHandle(socketChannel, readBuffer));
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        logger.error(exc.getMessage(), exc);
    }
}

/**
 * 负责对每一个socketChannel的数据获取事件进行监听。<p>
 * <p>
 * 重要的说明: 一个socket channel都会有一个独立工作的SocketChannelReadHandle对象(CompletionHandler接口的实现)，
 * 其中又都将独享一个“文件状态标示”对象FileDescriptor、
 * 一个独立的由程序员定义的Buffer缓存(这里我们使用的是ByteBuffer)、
 * 所以不用担心在服务器端会出现“窜对象”这种情况，因为JAVA AIO框架已经帮您组织好了。<p>
 * <p>
 * 但是最重要的，用于生成channel的对象: AsynchronousChannelProvider是单例模式，无论在哪组socketchannel，
 * 对是一个对象引用(但这没关系，因为您不会直接操作这个AsynchronousChannelProvider对象)。
 *
 * @author yinwenjie
 */
class SocketChannelReadHandle implements CompletionHandler<Integer, StringBuffer> {
    private final static Logger logger = LoggerFactory.getLogger(SocketChannelReadHandle.class);

    private AsynchronousSocketChannel socketChannel;

    /**
     * 专门用于进行这个通道数据缓存操作的ByteBuffer<br>
     * 当然，您也可以作为CompletionHandler的attachment形式传入。<br>
     * 这是，在这段示例代码中，attachment被我们用来记录所有传送过来的Stringbuffer了。
     */
    private ByteBuffer byteBuffer;

    public SocketChannelReadHandle(AsynchronousSocketChannel socketChannel, ByteBuffer byteBuffer) {
        this.socketChannel = socketChannel;
        this.byteBuffer = byteBuffer;
    }

    @Override
    public void completed(Integer result, StringBuffer historyContext) {
        // 如果条件成立，说明客户端主动终止了TCP套接字，这时服务端种植就可以了
        if (result == -1) {
            try {
                this.socketChannel.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        logger.info("completed(Integer result, Void attachment) : 然后我们来取出通道中准备好的值");

        /**
         * 实际上，由于我们从Integer resul知道了本次channel从操作系统获取数据种长度
         * 所以实际上，我们不需要切换读模式，但是为了保证编码的规范性，还是建议进行切换。
         *
         * 另外，无论是java aio框架还是java nio框架，都会出现buffer的总容量小于当前从操作系统获取到的总数据量，
         * 但区别是，java aio框架中，我们不需要专门考虑处理这样的情况，因为java aio框架已经帮我们做了处理（做成了多次通知）
         */
        this.byteBuffer.flip();
        byte[] contexts = new byte[1024];
        this.byteBuffer.get(contexts, 0, result);
        this.byteBuffer.clear();
        String nowContent = new String(contexts, 0, result, StandardCharsets.UTF_8);
        historyContext.append(nowContent);
        logger.info("================目前的传输结果: {}", historyContext);

        // 如果条件成立，说明还没有接收到“结束标记”
        if (historyContext.indexOf("over") == -1) {
            return;
        }

        //=========================================================================
        //          和上篇文章的代码相同，我们以“over”符号作为客户端完整信息的标记
        //=========================================================================
        logger.info("=======收到完整信息，开始处理业务=========");
        historyContext = new StringBuffer();

        // 还要继续监听(一次监听一次通知)
        this.socketChannel.read(this.byteBuffer, historyContext, this);
    }

    @Override
    public void failed(Throwable exc, StringBuffer historyContext) {
        logger.info("=====发现客户端异常关闭，服务器将关闭TCP通道");
        try {
            this.socketChannel.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}