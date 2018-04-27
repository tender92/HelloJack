package com.tender.algorithm.integer;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by boyu on 2018/4/26.
 * 扑克牌的顺子
 * 从扑克牌中随机抽 5 张牌,判断是不是顺子,即这 5 张牌是不是连续的。 2-10 为数字本身,A 为 1,J 为 11,Q 为 12,K 为 13,而大小王可以看成任意的数字。
 * 把大小王的输入看作是0；先对输入的数组排序；求出该数组中值为0的元素个数为numZero；其它相邻两元素之差减1的结果之和为numGap；只要numZero大于numGap，则五张牌可连续。
 */

public class PokerSerial {
    public boolean isContinuous(int[] array) {
        if (array == null) {
            return false;
        }
        Arrays.sort(array);
        int numZero = 0;
        int numGap = 0;
        while (array[numZero] == 0) {
            numZero ++;
        }
        int small = numZero;
        int big = numZero + 1;
        while (big < array.length) {
            if (array[small] == array[big]) {//存在对子，不连续
                return false;
            }
            numGap += array[big] - array[small] - 1;
            small = big;
            big ++;
        }
        return numGap <= numZero;
    }

    public static void main(String[] args) {
        System.out.println("请输入扑克牌的代表数字（2-10为数字本身，A为1，J为11，Q为12，K为13，而大小王为0）：");
        Scanner scanner = new Scanner(System.in);
        int[] num = new int[5];
        for (int i = 0; i < 5; i ++) {
            num[i] = scanner.nextInt();
        }
        System.out.println("5张牌可否连续？" + new PokerSerial().isContinuous(num));

    }
}
