package com.tam.tomcat;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    // 存放静态资源的位置
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "tomcat" + File.separator + "webroot";

    // 关闭Server的请求
    private static final String SHUTDOWN_COMMAND = "shutdown";

    // 是否关闭Server
    private boolean shutdown = false;

    // 主入口
    public static void main(String[] args) {
        final var server = new HttpServer();
        server.await();
    }

    private void await() {
        // 启动ServerSocket
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        while (!shutdown) {
            // 创建socket，有请求的时触发
            try (Socket socket = serverSocket.accept();
                 InputStream input = socket.getInputStream();
                 OutputStream output = socket.getOutputStream()) {
                // 封装input至request，并处理请求
                final var request = new Request(input);
                request.parse();

                // 封装output至response
                final var response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // 如果接受的是关闭请求，则设置关闭监听request的标志
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
