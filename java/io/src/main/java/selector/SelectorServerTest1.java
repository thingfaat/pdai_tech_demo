package selector;

import java.io.IOException;
import java.nio.channels.Selector;

public class SelectorServerTest1 {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        System.out.println(selector.getClass()); // 在mac上是class sun.nio.ch.KQueueSelectorImpl
    }
}
