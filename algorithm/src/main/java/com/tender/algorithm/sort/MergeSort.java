package com.tender.algorithm.sort;

import com.tender.algorithm.tools.ArrayUtil;

/**
 * Created by boyu on 2018/4/20.
 * 归并排序
 * 依次二分，直到最小层级数组可以有序合并，两个连续数组合并的时候引入一个缓存数组辅助排序，直到所有数组合并。
 */

public class MergeSort {
    public static int[] sort(int[] array, int low, int high) {
        int middle = (low + high) / 2;
        if (low < high) {
            sort(array, low, middle);
            sort(array, middle + 1, high);

            merge(array, low, middle, high);
        }
        return array;
    }

    public static void merge(int[] array, int low, int middle, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = middle + 1;
        int k = 0;
        while (i <= middle && j <= high) {
            if (array[i] < array[j]) {
                temp[k ++] = array[i ++];
            } else {
                temp[k ++] = array[j ++];
            }
        }
        while (i <= middle) {
            temp[k ++] = array[i ++];
        }
        while (j <= high) {
            temp[k ++] = array[j ++];
        }
        for (int t = 0; t < temp.length; t ++) {
            array[low + t] = temp[t];
        }
        ArrayUtil.printArray(array);
    }

    public static void main(String[] args) {
        int[] array = ArrayUtil.randomArray(10);
        sort(array, 0, array.length - 1);
        System.out.print("排序后结果数组：");
        ArrayUtil.printArray(array);
    }
}
