package thread.test1;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class ImmutableTest {
    private final static Logger logger = LoggerFactory.getLogger(ImmutableTest.class.getName());

    private static Vector<Integer> vector = new Vector<>();

    @Test
    public void test1() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<Object, Object> unmodifiableMap = Collections.unmodifiableMap(map);
        unmodifiableMap.put("name", "张三"); // 出现报错：java.lang.UnsupportedOperationException
    }

    @Test
    public void test2() throws Exception {
        while (true) {
            for (int i = 0; i < 100; i++) {
                vector.add(i);
            }

            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            });

            executorService.execute(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.get(i);
                }
            });
            executorService.shutdown();
        }
    }

    @Test
    public void test3() throws Exception {
        LockSupport.parkNanos(1000); // 有限等待
    }

    @Test
    public void test4() throws Exception {
        new Thread(new MyRunnable()).start();
    }

    @Test
    public void test5() throws Exception {
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);
        new Thread(futureTask).start();
    }

    @Test
    public void test6() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new MyRunnable());
        }
        executorService.shutdown();
    }

    @Test
    public void test7() throws Exception {
        Thread thread = new Thread(MyRunnable::new);
        thread.setDaemon(true); // 设置为守护线程，但其他非守护线程都已经销毁后，守护线程也会主动关闭
        thread.start();
    }

    @Test
    public void test8() throws Exception {
        Thread thread = new Thread(() -> {
            logger.info("已经完成了当前线程主要的任务，就可以让出cpu给其他线程执行了");
            Thread.yield(); // 让出cpu执行权
        });
        thread.start();
        thread.join();
    }

    @Test
    public void test9() throws Exception {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        });
        thread.start();
        thread.interrupt(); // 中断线程
    }

    @Test
    public void test10() throws Exception {
        Thread thread = new Thread(() -> {
            while (!Thread.interrupted()) {
                logger.info("执行中...");
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt(); // 中断线程
    }

    @Test
    public void test11() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        });
        executorService.shutdown(); // 不再接收新的任务，如果当前还是任务还没有执行完，接着执行
        executorService.shutdownNow(); // 不再接收新的任务，并且终止当前在执行的任务
    }

    /**
     * 如果想要中断任务池中的一个线程，可以通过submit方法来提交一个任务，会返回一个Future对象，
     * 可以通过这个对象的cancel(true)实现中断，true就是如果在运行过程中也中断
     *
     * @throws Exception
     */
    @Test
    public void test12() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<?> future = executorService.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        });
        future.cancel(true);
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("线程输出");
    }
}

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("线程异步执行");
        return 1;
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("异步线程执行");
    }
}
