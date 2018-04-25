package com.tender.algorithm.sort;

import com.tender.algorithm.tools.ArrayUtil;

/**
 * Created by boyu on 2018/4/23.
 * 选择排序
 * 二层循环：外层循环控制层数，内层循环依次找到最小值放到未排序子序列首部。
 */

public class SelectSort {
    public static void selectSort(int[] array) {
        int min;
        int temp;
        for (int i = 0; i < array.length; i ++) {
            min = array[i];
            for (int j = i + 1; j < array.length; j ++) {
                if (array[j] < min) {
                    System.out.print("交换数据array[" + i + "]=" + array[i] + " 和 array[" + j + "]=" + array[j] + "\n");
                    min = array[j];
                    temp = array[i];
                    array[i] = min;
                    array[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = ArrayUtil.randomArray(10);
        selectSort(array);
        System.out.print("排序后结果数组：");
        ArrayUtil.printArray(array);
    }
}
