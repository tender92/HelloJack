package com.tender.algorithm.sort;

import com.tender.algorithm.tools.ArrayUtil;

/**
 * Created by boyu on 2018/4/23.
 * 冒泡排序
 * 二层循环，外部循环控制次数，内部循环每次找到最小值往前排
 */

public class BubbleSort {
    public static void bubbleSort(int[] array) {
        int temp;
        for (int i = 0; i < array.length - 1; i ++) {
            for (int j = i + 1; j < array.length; j ++) {
                if (array[i] > array[j]) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            ArrayUtil.printArray(array);
        }
    }

    public static void main(String[] args) {
        int[] array = ArrayUtil.randomArray(10);
        bubbleSort(array);
        System.out.print("排序后结果数组：");
        ArrayUtil.printArray(array);
    }
}
