package thread.test11;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {
    private int[] array;
    private int low, high;
    private static final int THRESHOLD = 1000;

    MergeSortTask(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (high - low < THRESHOLD) {
            Arrays.sort(array, low, high); // 小数组直接排序
        } else {
            int mid = low + (high - low) / 2;
            invokeAll(new MergeSortTask(array, low, mid), new MergeSortTask(array, mid, high));
            merge(mid); // 合并两个有序部分
        }
    }

    private void merge(int mid) {
        // 实现归并两个已排序的子数组
        // 可使用临时数组等标准归并逻辑
    }
}
