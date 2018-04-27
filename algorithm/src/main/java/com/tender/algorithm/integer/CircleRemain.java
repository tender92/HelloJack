package com.tender.algorithm.integer;

import java.util.Scanner;

/**
 * Created by boyu on 2018/4/26.
 * 圆圈中最后剩下的数字
 * 0,1,...,n-1这n个数字排成一个圆圈，从数字0开始每次从这个圆圈里删除第m个数字。求这个圆圈里剩下的最后一个数字。
 * 递归表达式：当n = 1时，输出结果f(n, m) = 0; 当n > 1时，f(n, m) = (f(n - 1, m) + m) % n。
 */

public class CircleRemain {
    public static int lastRemaining(int n, int m){
        if(n < 1 || m < 1){
            return -1;
        }
        int last = 0;
        for(int i = 2; i <= n; i++){
            last = (last + m) % i;
        }
        return last;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入圆圈的大小：");
        int n = scanner.nextInt();
        System.out.print("请输入删除数字的所需走的步数：");
        int m = scanner.nextInt();
        System.out.print("最后剩下的数字是：" + lastRemaining(n, m));
    }
}
