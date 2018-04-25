package com.tender.algorithm.sort;

import com.tender.algorithm.tools.ArrayUtil;

/**
 * Created by boyu on 2018/4/23.
 * 插入排序
 * 二层循环：外层循环控制层数，内部循环控制向前面数组有序插入一个数据
 */

public class InsertSort {
    public static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i ++) {
            int temp = array[i];
            int j;
            for (j = i -1; j >= 0; j --) {//将大于temp的元素往后移
                if (array[j] > temp) {
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            array[j + 1] = temp;
            ArrayUtil.printArray(array);
        }
    }

    public static void main(String[] args) {
        int[] array = ArrayUtil.randomArray(10);
        insertSort(array);
        System.out.print("排序后结果数组：");
        ArrayUtil.printArray(array);
    }
}
