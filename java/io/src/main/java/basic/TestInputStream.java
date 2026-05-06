package basic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class TestInputStream {
    private InputStream inputStream;
    private static final String CONTENT = "hello world";

    @Before
    public void setUp() throws Exception {
        this.inputStream = TestInputStream.class.getResourceAsStream("/input.txt");
    }

    @Test
    public void testReadAllBytes() throws Exception {
        String content = new String(this.inputStream.readAllBytes());
        System.out.println(content);
        Assert.assertEquals(CONTENT, content);
    }

    @Test
    public void testReadNBytes() throws Exception {
        byte[] data = new byte[5];
        this.inputStream.readNBytes(data, 0, 5);
        System.out.println(new String(data));
    }

    @Test
    public void testTransfer() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        this.inputStream.transferTo(bos);
        Assert.assertEquals(CONTENT, bos.toString());
    }
}
