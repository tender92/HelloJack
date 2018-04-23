package com.tender.sort_algorithm;

/**
 * Created by boyu on 2018/4/23.
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
            RandomUtil.printArray(array);
        }
    }

    public static void main(String[] args) {
        int[] array = RandomUtil.randomArray(10);
        bubbleSort(array);
        System.out.print("排序后结果数组：");
        RandomUtil.printArray(array);
    }
}
