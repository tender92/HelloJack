package com.tender.algorithm.integer;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by boyu on 2018/4/27.
 * 给定一个不重复数组和一个目标值，输出数组中所有两个元素之后为目标值的下标对。
 * 利用HashMap处理两个数之和为目标值的关系，for循环处理对于每一个元素，判断目标值与该元素的差是否存在map中，存在的话将这两个数的下标存入结果HashMap中；不存在的话将目标值与该元素的差与该元素的下标存入map中。
 */

public class TwoSum {
    public HashMap<Integer, Integer> twoSum(int[] array, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < array.length; i ++) {
            if (map.get(array[i]) != null) {
                result.put(map.get(array[i]) + 1, i + 1);
            }
            map.put((target - array[i]), i);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("请输入一个整数数组：");
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[4];
        int target;
        for (int i = 0; i < array.length; i ++) {
            array[i] = scanner.nextInt();
        }
        System.out.println("请输入要得到的目标整数：");
        target = scanner.nextInt();
        HashMap<Integer, Integer> result = new TwoSum().twoSum(array, target);
        if (result != null && result.keySet().size() > 0) {
            for (int key: result.keySet()) {
                System.out.println("成功找到了index" + key + " + index" + result.get(key) + " = " + target);
            }
        } else {
            System.out.println("未找到两个数之和为" + target);
        }
    }
}
