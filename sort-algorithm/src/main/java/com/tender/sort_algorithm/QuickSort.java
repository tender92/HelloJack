package com.tender.sort_algorithm;

/**
 * Created by boyu on 2018/4/19.
 */

public class QuickSort {
    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int temp;//array 中的基准数
            int t;//交换一大一小在 array 中位置的暂时值
            int i, j;
            temp = array[left];
            i = left;
            j = right;
            while (i != j) {
                while (array[j] >= temp && i < j) {
                    j --;
                }
                while (array[i] <= temp && i < j) {
                    i ++;
                }
                if (i < j) {
                    System.out.print("要进行交换了：i = " + i + ";j = " + j + "\n");
                    t = array[i];
                    array[i] = array[j];
                    array[j] = t;
                }
                RandomUtil.printArray(array);
            }
            System.out.print("基数要进行交换了：i = " + left + ";j = " + j + "\n");
            //将基准数归位
            array[left] = array[i];
            array[i] = temp;
            quickSort(array, left, i - 1);
            quickSort(array, i + 1, right);
        }
    }

    public static void main(String[] args) {
        int[] array = new int[10];
        array = RandomUtil.randomArray(array.length);
        quickSort(array, 0, array.length - 1);
        System.out.print("排序后结果数组：");
        RandomUtil.printArray(array);
    }
}
