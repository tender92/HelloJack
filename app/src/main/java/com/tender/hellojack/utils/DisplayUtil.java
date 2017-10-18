package com.tender.hellojack.utils;

import android.content.Context;

/**
 * 
 * @author boyu on 17/1/20.
 * 
 */
public class DisplayUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = App.getScreenMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = App.getScreenMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = App.getScreenMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param context
	 * @param spValue
	 *            将sp值转换为px值，保证文字大小不变
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = App.getScreenMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

}
