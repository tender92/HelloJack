package com.tender.sort_algorithm;

/**
 * Created by boyu on 2018/4/23.
 * 希尔排序
 * 三层循环：最外层层数，内部二层循环控制逐次递减的间隔数比大小交换
 */

public class HeerSort {
    public static void heerSort(int[] array) {
        int d = array.length / 2;
        while (true) {
            for (int i = 0; i < d; i ++) {
                for (int j = i; j + d < array.length; j += d) {
                    int temp;
                    if (array[j] > array[j + d]) {
                        temp = array[j];
                        array[j] = array[j + d];
                        array[j + d] = temp;
                        RandomUtil.printArray(array);
                    }
                }
            }
            if (d == 1) {
                break;
            }
            d --;
        }
    }

    public static void main(String[] args) {
        int[] array = RandomUtil.randomArray(10);
        heerSort(array);
        System.out.print("排序后结果数组：");
        RandomUtil.printArray(array);
    }
}
