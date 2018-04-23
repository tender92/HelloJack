package com.tender.sort_algorithm;

/**
 * Created by boyu on 2018/4/23.
 */

public class SelectSort {
    public static void selectSort(int[] array) {
        int min;
        int temp;
        for (int i = 0; i < array.length; i ++) {
            min = array[i];
            for (int j = i; j < array.length; j ++) {
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
        int[] array = RandomUtil.randomArray(10);
        selectSort(array);
        System.out.print("排序后结果数组：");
        RandomUtil.printArray(array);
    }
}
