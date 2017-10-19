package com.tender.hellojack.utils;

import android.os.Bundle;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by boyu on 2017/9/6.
 */

public class StringUtil {

    public static final String REGEX_BLANK = "\\s";

    /**
     * 字符串是否包含空格字符
     * @param str
     */
    public static boolean hasBlank(String str) {
        Pattern blank = Pattern.compile(REGEX_BLANK);
        Matcher blank_m = blank.matcher(str);
        if (blank_m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 字符是否为空字符
     * @param cs
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen = 0;
        if ((cs == null) || ((strLen = cs.length()) == 0))
            return true;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否有值
     * @param value
     */
    public static boolean hasValue(String value) {
        return !TextUtils.isEmpty(value);
    }

    /**
     * 判断str1中包含str2的个数
     * @param count
     * @param str1
     * @param str2
     * @param pos
     */
    public static ArrayList<Integer> countStr(
            ArrayList<Integer> count, String str1, String str2, int pos) {
        if (!str1.contains(str2)) {
            return count;
        } else {
            count.add(str1.indexOf(str2) + pos);
            pos = str1.indexOf(str2) + str2.length();
            countStr(count, str1.substring(pos), str2, pos);
            return count;
        }
    }

    /**
     * 获取首行字符串
     * @param str
     * @return
     */
    public static String getFirstLineOfStr(String str) {
        String result = str;
        try {
            if (str != null && str.contains("\n")) {
                result = str.substring(0, str.indexOf("\n"));
            }
        } catch (Exception e) {
            Logger.d("getFirstLineOfStr error:" + e.getMessage());
        }
        return result;
    }

    /**
     * 判定输入汉字
     * @param c
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     * @param name
     */
    public static boolean checkNameChese(String name) {
        String pattern = "[\u4E00-\u9FA5]{2,30}(?:·[\u4E00-\u9FA5]{1,30})*";
        return Pattern.matches(pattern, name);
    }

    /**
     * 格式化输出bundle
     * @param bundle
     */
    public static String printBundle(Bundle bundle) {
        if (bundle == null) {
            return "bundle为空";
        }
        StringBuilder sb = new StringBuilder("");
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            sb.append(key).append(":").append(bundle.get(key)).append(";");
        }
        return sb.toString();
    }

    public static boolean equals(String str1, String str2){
        return str1.equals(str2);
    }
}
