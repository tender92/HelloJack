package com.tender.tools.views.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tender.tools.utils.ui.DrawableFactory;
import com.tender.tools.utils.ui.ScreenUtil;

/**
 * Created by boyu on 2018/1/25.
 */

public class PagerIndicator {
    private LinearLayout mContainer;
    private int size;
    private int wh;
    private int doublePadding;
    private int normalIndicatorColor = Color.LTGRAY;
    private int currentIndicatorColor = Color.LTGRAY;

    private SparseArray<View> marks;
    private SparseArray<Integer> marksLeft;

    /**
     *
     * @param context
     * @param mContainer 该指示器的父类容器
     * @param size 总数
     * @param currentPosition 设置默认显示第几个
     */
    public PagerIndicator(Context context, LinearLayout mContainer, int size, int currentPosition) {
        this.mContainer = mContainer;
        this.size = size;
        initMarks(context, currentPosition);
    }

    /**
     *
     * @param context
     * @param mContainer 该指示器的父类容器
     * @param size 总数
     * @param currentPosition 设置默认显示第几个
     * @param normalColor 默认点的颜色
     * @param currentColor 当前点的颜色
     *
     */
    public PagerIndicator(Context context, LinearLayout mContainer, int size, int currentPosition,
                          int normalColor, int currentColor) {
        this.mContainer = mContainer;
        this.size = size;
        this.normalIndicatorColor = normalColor;
        this.currentIndicatorColor = currentColor;
        initMarks(context, currentPosition);
    }

    private void initMarks(Context context, int currentPosition) {

        if (size > 0) {

            marks = new SparseArray<View>();
            marksLeft = new SparseArray<Integer>();
            float density = ScreenUtil.getDensity(context);
            wh = (int) (density * 6 + 0.5f);
            int padding = (int) (density * 2 + 0.5f);

            float[] endRadius = new float[] { wh, wh, wh, wh, wh, wh, wh, wh };
            Drawable circleDrawable = DrawableFactory.getCircleDrawable(normalIndicatorColor,	wh);
            Drawable ovalDrawable = DrawableFactory.createShape(currentIndicatorColor,	endRadius);

            doublePadding = padding * 2;
            LinearLayout.LayoutParams circleParams = new LinearLayout.LayoutParams(wh + doublePadding, wh);
            LinearLayout.LayoutParams ovalParams = new LinearLayout.LayoutParams(wh * 2 + doublePadding,	wh);

            for (int i = 0; i < size; i++) {

                ImageView mark = new ImageView(context);
                mark.setTag("mark" + i);
                marks.put(i, mark);
                mark.setPadding(padding, 0, padding, 0);

                if (i == currentPosition) {
                    mark.setImageDrawable(ovalDrawable);
                    mContainer.addView(mark, ovalParams);
                } else {
                    mark.setImageDrawable(circleDrawable);
                    mContainer.addView(mark, circleParams);
                }
            }

            mContainer.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {

                            mContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            for (int i = 0; i < size; i++) {
                                marksLeft.put(i, marks.get(i).getLeft());
                            }
                        }
                    });
        }

    }

    /**
     * see ViewPager.OnPageChangeListener.onPageScrolled
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int toPosition = position + 1;
        if(position + 1 < size) {
            if(marksLeft.size() > 0 && positionOffset > 0) {
                int startX = marksLeft.get(position);
                int endX = marksLeft.get(toPosition);

                if(position == 0) {

                    marks.get(0).setTranslationX((endX - (startX + wh)) * positionOffset);
                    marks.get(toPosition).setTranslationX((startX - endX) * positionOffset);
                } else if(toPosition == marks.size() - 1) {

                    marks.get(0).setTranslationX(startX - wh * 2 - doublePadding + (endX - startX) * positionOffset);
                    marks.get(toPosition).setTranslationX((startX - wh - endX) * positionOffset);

                } else {

                    marks.get(0).setTranslationX(startX - wh * 2 - doublePadding + (endX - startX) * positionOffset);
                    marks.get(toPosition).setTranslationX((startX - endX - wh) * positionOffset);
                }

            }
        }
    }
}
