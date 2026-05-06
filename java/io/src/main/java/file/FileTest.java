package file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;

public class FileTest {

    @Test
    public void listAllFiles() throws Exception {
        String path = Objects.requireNonNull(FileTest.class.getResource("/")).getPath();
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            System.out.println(file.getName());
        }
    }

    @Test
    public void copyFIle() throws Exception {
        String srcPath = Objects.requireNonNull(FileTest.class.getResource("/")).getPath() + "input.txt";
        String destPath = Objects.requireNonNull(FileTest.class.getResource("/")).getPath() + "output.txt";

        try (FileInputStream fis = new FileInputStream(srcPath); FileOutputStream fos = new FileOutputStream(destPath)) {
            byte[] buffer = new byte[20 * 1024];
            while (fis.read(buffer, 0, buffer.length) != -1) {
                fos.write(buffer);
            }
        }
    }
}
