package com.tam.tomcat;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 请求
 */
public class Request {
    private final InputStream input;

    @Getter
    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
        final var request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            i = -1;
        }

        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.println(request);
        /// GET /hello.txt HTTP/1.1
        /// User-Agent: IntelliJ HTTP Client/IntelliJ IDEA 2026.1
        /// Accept-Encoding: br, deflate, gzip, x-gzip
        /// Accept: */*
        /// host: localhost:8080

        // 获取两个空格之间的内容，这里将HttpServer.WEB_ROOT中静态文件的文件名称
        uri = parseUri(request.toString());
    }

    private String parseUri(final String request) {
        int index1, index2;
        index1 = request.indexOf(' ');
        if (index1 != -1) {
            index2 = request.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return request.substring(index1 + 1, index2);
            }
        }
        return null;
    }
}
