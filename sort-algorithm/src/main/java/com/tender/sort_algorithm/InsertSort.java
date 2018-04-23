package com.tender.sort_algorithm;

/**
 * Created by boyu on 2018/4/23.
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
            RandomUtil.printArray(array);
        }
    }

    public static void main(String[] args) {
        int[] array = RandomUtil.randomArray(10);
        insertSort(array);
        System.out.print("排序后结果数组：");
        RandomUtil.printArray(array);
    }
}
