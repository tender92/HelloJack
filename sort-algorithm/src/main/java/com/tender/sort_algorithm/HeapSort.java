package com.tender.sort_algorithm;

/**
 * Created by boyu on 2018/4/23.
 */

public class HeapSort {
    /**
     * 构建大根堆
     * array[i]为根节点，array[j]为左孩子，array[j + 1]为右孩子
     * @param array
     * @param i
     * @param len
     */
    public static void adjustHeap(int[] array, int i, int len) {
        int temp = array[i];//根节点
        int j;
        for (j = i * 2 + 1; j <= len; j = j * 2) {//沿关键字较大的孩子向下筛选
            if (j < len && array[j] < array[j + 1]) {
                ++ j;
            }
            if (temp >= array[j]) {//根节点大于孩子
                break;
            }
            array[i] = array[j];//提取最大值到根节点
            i = j;
        }
        array[i] = temp;//最大值和根节点交换
        RandomUtil.printArray(array);
    }

    public static void heapSort(int[] array) {
        int i;
        for (i = array.length / 2 - 1; i >= 0; i --) {//整个数组构建大根堆
            adjustHeap(array, i, array.length - 1);
        }
        for (i = array.length - 1; i >= 0; i --) {//将堆顶记录和未经排序子序列的最后一个记录交换
            System.out.print("交换大根堆堆顶" + array[0] + " 和未经排序子序列的最后一个记录 " + array[i] + "\n");
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            adjustHeap(array, 0, i -1);//将数组array中的前 i - 1个记录重新构建大根堆
        }
    }

    public static void main(String[] args) {
        int[] array = RandomUtil.randomArray(10);
        heapSort(array);
        System.out.print("排序后结果数组：");
        RandomUtil.printArray(array);
    }
}
