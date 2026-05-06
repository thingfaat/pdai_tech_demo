package aio;

import java.io.OutputStream;
import java.net.Socket;

public class AIOClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 83);
        OutputStream out = socket.getOutputStream();
        String s = "hello world";
        out.write(s.getBytes());
        out.close();
        socket.close();
    }
}
