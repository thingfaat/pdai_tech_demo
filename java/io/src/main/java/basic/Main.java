package basic;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString("张三".getBytes(StandardCharsets.UTF_8)));
    }
}
