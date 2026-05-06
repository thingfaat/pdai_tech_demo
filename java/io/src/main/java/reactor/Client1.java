package reactor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client1 {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 8888)) {
            OutputStream output = socket.getOutputStream();
            output.write("打招呼".getBytes(StandardCharsets.UTF_8));
        }
    }
}
