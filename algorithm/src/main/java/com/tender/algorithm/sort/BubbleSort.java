package com.tender.algorithm.sort;

import com.tender.algorithm.tools.ArrayUtil;

/**
 * Created by boyu on 2018/4/23.
 * 冒泡排序
 * 二层循环，外部循环控制次数，内部循环每次比较相邻两个元素，前大于后则交换。
 * 每一个内循环把最大的元素放置到最后。
 */

public class BubbleSort {
    public static void bubbleSort(int[] array) {
        int temp;
        for (int i = 0; i < array.length - 1; i ++) {
            for (int j = 0; j < array.length - 1 - i; j ++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
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
