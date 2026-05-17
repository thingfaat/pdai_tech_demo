package thread.test11;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 100;
    private final long[] array;
    private final int start;
    private final int end;

    public SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 问题足够小，直接计算
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // 任务太大。一分为二
            int middle = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, middle);
            SumTask rightTask = new SumTask(array, middle, end);
            leftTask.fork(); // 异步执行左边部分
            rightTask.fork(); // 异步执行右边部分
            return leftTask.join() + rightTask.join(); // 合并结果
        }
    }

    public static void main(String[] args) {
        long[] array = new long[100_00];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(array, 0, array.length);
        Long result = task.invoke();
        log.info("result:{}", result);
    }
}
