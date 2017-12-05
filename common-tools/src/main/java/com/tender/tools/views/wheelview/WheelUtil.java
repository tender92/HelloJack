package com.tender.tools.views.wheelview;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WheelUtil {

    public static final int START_YEAR = 1900;

    /**
     * 判断是否为闰年
     *
     * @param yearText
     * @return
     */
    public static boolean isLeapYear(int yearText) {
        boolean f = false;
        if ((yearText % 4 == 0 && yearText % 100 != 0) || yearText % 400 == 0) {
            f = true;
        }
        return f;
    }

    /**
     * 得到年份列表 关于数据填充实现的不好，还有其他的方式，暂时就这样了。。。
     *
     * @return
     */
    public static List<WheelModel> getYearItems() {
        Calendar a = Calendar.getInstance();
        List<WheelModel> list = new ArrayList<>();
        for (int i = START_YEAR; i <= a.get(Calendar.YEAR); i++) {
            list.add(new WheelModel(String.valueOf(i), null, String.valueOf(i) + "年"));
        }
        return list;
    }

    /**
     * 得到月份列表
     *
     * @return
     */
    public static List<WheelModel> getMonthItems() {
        List<WheelModel> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(new WheelModel(String.valueOf(i), null, String.valueOf(i) + "月"));
        }
        return list;
    }

    /**
     * 得到指定年月的天数列表
     *
     * @param year
     * @param month
     * @return
     */
    public static List<WheelModel> getDayItems(int year, int month) {
        List<WheelModel> list = new ArrayList<>();
        int l = getDaysByYearMonth(year, month);
        for (int i = 1; i <= l; i++) {
            list.add(new WheelModel(String.valueOf(i), null, String.valueOf(i) + "日"));
        }
        return list;
    }

    /**
     * 获取指定年份、月份的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 闰年
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
