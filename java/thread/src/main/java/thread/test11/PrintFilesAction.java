package thread.test11;

import lombok.extern.slf4j.Slf4j;

import javax.naming.PartialResultException;
import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

@Slf4j
public class PrintFilesAction extends RecursiveAction {
    private File dir;

    public PrintFilesAction(File dir) {
        this.dir = dir;
    }

    @Override
    protected void compute() {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    new PrintFilesAction(file).fork(); // 子目录递归 fork
                } else {
                    log.info(file.getAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) {
        int parallelism = ForkJoinPool.getCommonPoolParallelism();
        log.info("parallelism:{}", parallelism);

        int parallelism1 = ForkJoinPool.commonPool().getParallelism();
        log.info("parallelism1:{}", parallelism1);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int poolSize = forkJoinPool.getPoolSize();
        int activeThreadCount = forkJoinPool.getActiveThreadCount();
        long queuedTaskCount = forkJoinPool.getQueuedTaskCount();
        long stealCount = forkJoinPool.getStealCount(); // 发生工作窃取的总次数
    }
}
