package com.tender.algorithm.integer;

import java.util.Scanner;

/**
 * Created by boyu on 2018/4/26.
 * 数值的整数次方
 */

public class Power {
    /**
     * 0的负次数幂无意义
     * 当指数为负数时，其结果为底数的指数绝对值次方取倒数
     * @param base
     * @param exponent
     * @return
     * @throws Exception
     */
    public double power(double base, int exponent) throws Exception {
        double result = 0.0;
        if (equal(base, 0.0) && exponent < 0) {
            throw new Exception("0的负次数幂无意义！！");
        }
        if (exponent < 0) {
            result = powerWithExponent(1.0 / base, - exponent);
        } else {
            result = powerWithExponent(base, exponent);
        }
        return result;
    }

    /**
     * exponent为偶数：result = result * powerWithExponent(base, exponent / 2)
     * exponent为奇数：result = result * powerWithExponent(base, (exponent - 1) / 2) * base
     * @param base
     * @param exponent
     * @return
     */
    private double powerWithExponent(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent == 1) {
            return base;
        }
        double result = powerWithExponent(base, exponent >> 1);
        result *= result;
        if ((exponent & 0x1) == 1) {
            result *= base;
        }
        return result;
    }

    // 判断两个double型数据，计算机有误差
    private boolean equal(double num1, double num2) {
        if ((num1 - num2 > -0.000001) && (num1 - num2) < 0.000001) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("底数为0，指数为负则退出本次计算。");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入底数：");
            double base = scanner.nextDouble();
            System.out.print("请输入指数：");
            int exponent = scanner.nextInt();
            Power power = new Power();
            try {
                System.out.println(base + "的" + exponent + "次方的结果为：" + power.power(base, exponent));
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
