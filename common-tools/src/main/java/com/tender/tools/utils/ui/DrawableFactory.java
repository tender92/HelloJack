package com.tender.tools.utils.ui;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * 类描述: 动态生产Drawable
 *
 */
public class DrawableFactory {
	
	/**
	 * 创建圆角背景
	 * @param fillColor			填充色
	 * @param roundRadius		四个角的弧度
	 * @return					Drawable
	 */
	public static GradientDrawable createShape(int fillColor, float roundRadius){
		GradientDrawable gd = new GradientDrawable();
	    gd.setColor(fillColor);
	    gd.setCornerRadius(roundRadius);
	    return gd;
	}
	
	/**
	 * 创建圆角背景
	 * @param fillColor			填充色
	 * @param roundRadius		四个角的弧度
	 * @return					Drawable
	 */
	public static GradientDrawable createShape(int fillColor, float []roundRadius){
		GradientDrawable gd = new GradientDrawable();
	    gd.setColor(fillColor);
	    gd.setCornerRadii(roundRadius);
	    return gd;
	}
	
	/**
	 * @Description 	创建圆形
	 * @param color		填充色
	 * @param size		直径
	 * @return
	 */
    public static GradientDrawable getCircleDrawable(int color, int size) {
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(color);
        circle.setCornerRadius(size / 2);
        circle.setSize(size, size);
        return circle;
    }
    /**
     * 获取左上和右上圆角矩形图片
     * @param width 指定图片的宽
     * @param height 指定图片的高
     * @param round 圆角的像素数
     * @param color 指定图片的颜色
     * @return
     */
	public static Drawable getRoundBitmap(int width, int height,int round, int color) {
		Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(b); 
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		
		RectF rf = new RectF(0, 0, width, round*2);
		canvas.drawRoundRect(rf, round, round, paint);
		
		Rect r = new Rect(0, round, width, (int) height);
		canvas.drawRect(r , paint); 
		 
		return new BitmapDrawable(b);
	}
	
	/**
	 * 有弧度(四个角弧度可以相同)有边框的Drawable
	 * 
	 * @param strokeWidth			边框宽
	 * @param strokeColor			边框颜色
	 * @param fillColor					填充色
	 * @param roundRadius			弧度值
	 * @return
	 */
	public static Drawable  getHollowRoundDrawable(
			int strokeWidth, int strokeColor, int fillColor, float roundRadius[]) {
		
		GradientDrawable gd = new GradientDrawable();
		gd.setStroke(strokeWidth, strokeColor);
		gd.setColor(fillColor);
	    gd.setCornerRadii(roundRadius);
	    return gd;
	}
	
	/**
	 * 有弧度(四个角弧度相同)有边框的Drawable
	 * 
	 * @param strokeWidth				边框宽
	 * @param strokeColor				边框颜色
	 * @param fillColor						填充色
	 * @param roundRadius				弧度值
	 * @return
	 */
	public static Drawable  getHollowRoundDrawable(
			int strokeWidth, int strokeColor, int fillColor, float roundRadius) {
		
		GradientDrawable gd = new GradientDrawable();
		gd.setStroke(strokeWidth, strokeColor);
		gd.setColor(fillColor);
	    gd.setCornerRadius(roundRadius);
	    return gd;
	}
}
