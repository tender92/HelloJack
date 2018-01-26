package com.tender.tools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tender.tools.R;
import com.tender.tools.utils.ui.DisplayUtil;

/**
 * Created by boyu on 2017/11/16.
 */

public class RainbowBar extends View {

    private int[] colors = new int[]{
            Color.parseColor("#1E88E5"),Color.parseColor("#d928a2"),
            Color.parseColor("#2db13f"),Color.parseColor("#c2cf11"),
            Color.parseColor("#b96731"),Color.parseColor("#ce0812")
    };
    private int barColor = Color.parseColor("#1E88E5");
    private int hspace = DisplayUtil.dip2px(getContext(), 80);
    private int vspace = DisplayUtil.dip2px(getContext(), 4);
    private float startX = 0;
    private float delta = 10f;
    private Paint mPaint;
    private int index = 0;

    public RainbowBar(Context context) {
        super(context);
    }

    public RainbowBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainbowBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.hj_tools_rainbow_bar, 0, 0);
        hspace = t.getDimensionPixelSize(R.styleable.hj_tools_rainbow_bar_hj_tools_hspace, hspace);
        vspace = t.getDimensionPixelSize(R.styleable.hj_tools_rainbow_bar_hj_tools_vspace, vspace);
        barColor = t.getColor(R.styleable.hj_tools_rainbow_bar_hj_tools_color, barColor);
        t.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(barColor);
        mPaint.setStrokeWidth(vspace);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sw = getMeasuredWidth();//获取View的宽度
        if (startX >= sw + (hspace + delta) - (sw % (hspace + delta))) {
            startX = 0;
        } else {
            startX += delta;
        }
        float start = startX;
        while (start < sw) {
            canvas.drawLine(start, 5, start + hspace, 5, mPaint);
            start += (hspace + delta);
        }

        start = startX - hspace - delta;
        while (start >= -hspace) {
            canvas.drawLine(start, 5, start + hspace, 5, mPaint);
            start -= (hspace + delta);
        }
        invalidate();
    }
}
