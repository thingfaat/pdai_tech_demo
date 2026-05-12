package thread.test10;

import java.util.concurrent.*;

public class FutureDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            long start = System.currentTimeMillis();
            while (true) {
                long current = System.currentTimeMillis();
                if ((current - start) > 1000) {
                    return 1;
                }
            }
        });

        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();
    }
}
