package com.tender.algorithm.tools;

/**
 * Created by boyu on 2018/4/19.
 */

public class ArrayUtil {
    public static int random100() {
        return (int)(Math.random() * 100 + 1);
    }

    public static int[] randomArray(int arrLength) {
        int[] array = new int[arrLength];
        System.out.print("初始化数组：");
        for (int i = 0; i < arrLength; i ++) {
            array[i] = random100();
            System.out.print(array[i]);
            System.out.print(" ");
        }
        System.out.print("\n");
        return array;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i ++) {
            System.out.print(array[i]);
            System.out.print(" ");
        }
        System.out.print("\n");
    }
}
