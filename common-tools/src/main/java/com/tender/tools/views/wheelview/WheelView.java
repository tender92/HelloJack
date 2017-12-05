package com.tender.tools.views.wheelview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WheelView extends View {
    public int oldIndex = -1;
    private float scaleX = 1.00F;
    private int widthMeasureSpec = 0;
    private int heightMeasureSpec = 0;
    public float itemHeightOuter;
    public float itemHeightCenter;
    private float bottom;
    private float top;
    private int baselineOuter;
    private int baselineCenter;
    public static final int WHEEL_LEFT = 234;
    public static final int WHEEL_CENTER = 354;
    public static final int WHEEL_RIGHT = 894;
    private int wheelGravity = WHEEL_CENTER;

    private int imageMarginLeft = 30;

    public void setWheelGravity(int wheelGravity) {
        this.wheelGravity = wheelGravity;
    }

    public enum ACTION {
        CLICK, FLING, DAGGLE
    }

    Context context;

    Handler handler;
    private GestureDetector gestureDetector;

    public OnItemSelectedListener getOnItemSelectedListener() {
        return onItemSelectedListener;
    }

    OnItemSelectedListener onItemSelectedListener;

    // Timer mTimer;
    ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    Paint paintOuterText;
    Paint paintCenterText;
    Paint paintIndicatorLine;

    Paint paintLayerTop, paintLayerButtom;

    public List<WheelModel> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

    List<WheelModel> items;

    // 显示几个条目
    int itemsVisible = 5;
    float textSizeCenter = 18;
    float textSizeOuter = 13;
    int textColorOuter = 0xff000000;
    int textColorCenter = 0xff4d4d4d;
    int lineColor = 0xffe6e6e6;
    boolean isLoop = false;
    float lineSpaceingDimens = 15;

    int maxTextWidth;
    int maxTextHeightCenter;
    int maxTextHeightOuter;

    // 第一条线Y坐标值
    int firstLineY;
    int secondLineY;

    int totalScrollY = 0;
    int initPosition = -1;
    private int selectedItem;
    int preCurrentIndex;
    int change;

    int measuredHeight;
    int measuredWidth;

    // 半圆周长
    int halfCircumference;
    // 半径
    int radius;

    private int mOffset = 0;
    private float previousY;
    long startTime = 0;

    private String defaultText = "中国";
    private int startDrawTextX = 0;

    private boolean isShowIcon = false;

    private Rect tempRect = new Rect();

    private RectF layerTopRect = new RectF();
    private RectF layerButtomRect = new RectF();

    public WheelView(Context context) {
        super(context);
        initWheelView(context);
    }

    public WheelView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        initWheelView(context, attributeset);
    }

    public WheelView(Context context, AttributeSet attributeset, int defStyleAttr) {
        super(context, attributeset, defStyleAttr);
        initWheelView(context, attributeset);
    }

    private void initWheelView(Context context, AttributeSet attributeset) {
        textSizeCenter = WheelUtil.dip2px(context, 18);
        textSizeOuter = WheelUtil.dip2px(context, 18);
        lineSpaceingDimens = WheelUtil.dip2px(context, 14);
        lineColor = 0xffb4b4b4;
        initWheelView(context);

    }

    private int getFixedItemsVisible(int originalNum) {
        if (originalNum < 3) {
            return 3;
        } else if (originalNum % 2 == 0) {
            return originalNum + 1;
        } else {
            return originalNum;
        }
    }

    private void initWheelView(Context context) {
        this.context = context;
        handler = new MessageHandler(this);
        gestureDetector = new GestureDetector(context, new WheelViewGestureListener(this));
        gestureDetector.setIsLongpressEnabled(false);

        initPaints();

    }

    public void reset() {
        totalScrollY = 0;
    }

    private void initPaints() {
        paintOuterText = new Paint();
        paintOuterText.setColor(textColorOuter);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTypeface(Typeface.MONOSPACE);
        paintOuterText.setTextSize(textSizeOuter);
        if (android.os.Build.VERSION.SDK_INT < 21) {
            paintOuterText.setAlpha(90);
        }

        paintCenterText = new Paint();
        paintCenterText.setColor(textColorCenter);
        paintCenterText.setAntiAlias(true);
        paintCenterText.setTypeface(Typeface.MONOSPACE);
        paintCenterText.setTextSize(textSizeOuter);

        paintIndicatorLine = new Paint();
        paintIndicatorLine.setColor(lineColor);
        paintIndicatorLine.setAntiAlias(true);

        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }


    private void remeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (items == null) {
            return;
        }

        measureTextWidthHeight();

        halfCircumference = (int) (itemHeightCenter * (itemsVisible - 1));
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        measuredHeight = (int) (itemHeightCenter + itemHeightOuter * (itemsVisible - 1));
        radius = (int) (halfCircumference / Math.PI);
        measuredWidth = maxTextWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        }
        firstLineY = (int) (itemHeightOuter * (itemsVisible - 1) / 2);
        secondLineY = (int) (itemHeightOuter * (itemsVisible - 1) / 2 + itemHeightCenter);
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (items.size() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }
        bottom = (items.size() - 1 - initPosition) * itemHeightOuter;
        top = -initPosition * itemHeightOuter;

        preCurrentIndex = initPosition;

        startDrawTextX = getTextX(defaultText, paintCenterText, tempRect);
        Log.d("WheelView", "startDrawTextX=" + startDrawTextX);

        paintLayerTop = new Paint();
        Shader mShaderTop = new LinearGradient(measuredWidth / 2, 0, measuredWidth / 2, firstLineY, new int[]{0xC8ffffff, 0x96ffffff, 0x64ffffff}, null, Shader.TileMode.CLAMP);
        paintLayerTop.setShader(mShaderTop);
        layerTopRect.set(0, 0, measuredWidth, firstLineY);
        paintLayerButtom = new Paint();
        Shader mShaderButtom = new LinearGradient(measuredWidth / 2, secondLineY, measuredWidth / 2, measuredHeight, new int[]{0x64ffffff, 0x96ffffff, 0xC8ffffff}, null, Shader.TileMode.CLAMP);
        paintLayerButtom.setShader(mShaderButtom);
        layerButtomRect.set(0, secondLineY, measuredWidth, measuredHeight);
    }

    private void measureTextWidthHeight() {
        for (int i = 0; i < items.size(); i++) {
            String s1 = items.get(i).getValue();
            paintCenterText.getTextBounds(s1, 0, s1.length(), tempRect);
            int textWidth = (int) paintCenterText.measureText(s1);
            if (textWidth > maxTextWidth) {
                maxTextWidth = (int) (textWidth * scaleX);
            }
        }
        paintCenterText.getTextBounds("\u661F\u671F", 0, 2, tempRect);
        maxTextHeightCenter = tempRect.height();

        paintOuterText.getTextBounds("\u661F\u671F", 0, 2, tempRect);
        maxTextHeightOuter = tempRect.height();


        itemHeightOuter = maxTextHeightOuter + (lineSpaceingDimens * 2);
        itemHeightCenter = maxTextHeightCenter + (lineSpaceingDimens * 2);

        Paint.FontMetricsInt fontMetricsOuter = paintOuterText.getFontMetricsInt();
        baselineOuter = (int) ((itemHeightOuter - fontMetricsOuter.bottom + fontMetricsOuter.top) / 2 - fontMetricsOuter.top);
        Paint.FontMetricsInt fontMetricsCenter = paintCenterText.getFontMetricsInt();
        baselineCenter = (int) ((itemHeightCenter - fontMetricsCenter.bottom + fontMetricsCenter.top) / 2 - fontMetricsCenter.top);
    }

    /**
     * 松手调用
     */
    void smoothScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            mOffset = (int) ((totalScrollY % itemHeightOuter + itemHeightOuter) % itemHeightOuter);
            if ((float) mOffset > itemHeightOuter / 2.0F) {
                mOffset = (int) (itemHeightOuter - (float) mOffset);
            } else {
                mOffset = -mOffset;
            }
        }
        mFuture = mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, mOffset), 0, 10, TimeUnit.MILLISECONDS);
    }


    protected final void scrollBy(float velocityY) {
        cancelFuture();
        // 修改这个值可以改变滑行速度
        int velocityFling = 15;
        mFuture = mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0, velocityFling, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    public final void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    public final void setIsShowIcon(boolean f) {
        this.isShowIcon = f;
    }


    private final void setInitPosition(int initPosition) {
        if (initPosition < 0) {
            this.initPosition = 0;
        } else {
            this.initPosition = initPosition;
        }
        oldIndex = initPosition;

        selectedItem = initPosition;
//        if (this.onItemSelectedListener != null) {
//            onItemSelectedListener.onItemSelected(initPosition);
//        }
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener) {
        onItemSelectedListener = OnItemSelectedListener;
    }

    private final void setItems(List<WheelModel> items) {
        reset();
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }

        remeasure(widthMeasureSpec, heightMeasureSpec);

        invalidate();
    }

    public final void setItems(List<WheelModel> items, int initPosition) {
        setInitPosition(initPosition);
        setItems(items);
    }

    public final String getSelectedItemKey() {
        if (selectedItem >= items.size() || selectedItem < 0)
            return "";
        return items.get(selectedItem).getKey();
    }

    public final String getSelectedItem() {
        if (selectedItem >= items.size() || selectedItem < 0)
            return "";
        return items.get(selectedItem).getValue();
    }

    public final int getSelectedPosition() {
        return selectedItem;
    }

    protected final void onItemSelected() {
        if (onItemSelectedListener != null) {
            postDelayed(new OnItemSelectedRunnable(this), 200L);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (items == null) {
            return;
        }

//        canvas.translate(0.0F, lineSpaceingDimens);//移动画布的原点坐标
        canvas.clipRect(0, 0, measuredWidth, measuredHeight);
        canvas.save();


        change = (int) (totalScrollY / itemHeightOuter);
        preCurrentIndex = initPosition + change % items.size();

        if (!isLoop) {
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > items.size() - 1) {
                preCurrentIndex = items.size() - 1;
            }
        } else {
            if (preCurrentIndex < 0) {
                preCurrentIndex = items.size() + preCurrentIndex;
            }
            if (preCurrentIndex > items.size() - 1) {
                preCurrentIndex = preCurrentIndex - items.size();
            }
        }

        int j3 = (int) (totalScrollY % itemHeightOuter);//滑动偏移量取余
        canvas.drawLine(0.0F, firstLineY, measuredWidth, firstLineY, paintIndicatorLine);
        canvas.drawLine(0.0F, secondLineY, measuredWidth, secondLineY, paintIndicatorLine);

        int j1 = 0;
        //当前item的top
        //最关键就是确定translateY的位置
        int translateY = getTranslateY(j1, j3);
        int translateYNext = getTranslateY(j1 + 1, j3);

        while (j1 < itemsVisible + 2) {
            String as; //需要显示出来的字符串
            Bitmap bitmap = null;
            int l1 = preCurrentIndex - (itemsVisible / 2 - j1) - 1;
            if (isLoop) {
                l1 = l1 % items.size();
                if (l1 < 0) {
                    l1 = l1 + items.size();
                }
                as = items.get(l1).getValue();
            } else if (l1 < 0) {
                as = "";
            } else if (l1 > items.size() - 1) {
                as = "";
            } else {
                as = items.get(l1).getValue();
                bitmap = items.get(l1).getBitmap();
            }
            //没有图标时，文字居中显示
            if (!isShowIcon) {
                startDrawTextX = getTextX(as, paintCenterText, tempRect);
            }
            canvas.save();

            canvas.translate(0.0F, translateY);//移动画布的原点坐标
            if (translateY < firstLineY && translateYNext > firstLineY) {
                // 条目经过第一条线
                canvas.save();
                canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY);//处于第一条线的条目的上半部分
                //clip之后坐标系并不会变化，只是未被clip的区域不会被绘制，tempRect是防止在ondraw中new对象
                canvas.drawText(as, startDrawTextX, baselineOuter, paintOuterText);
                if (bitmap != null && isShowIcon)
                    canvas.drawBitmap(bitmap, startDrawTextX - bitmap.getWidth() - imageMarginLeft, baselineOuter - maxTextHeightOuter + 5, paintOuterText);

                canvas.restore();//恢复到上一次save的地方
                canvas.save();
                canvas.clipRect(0, firstLineY - translateY, measuredWidth, (int) (itemHeightCenter));//处于第一条线的条目的下半部分
                canvas.drawText(as, startDrawTextX, baselineCenter, paintCenterText);//clip之后坐标系并不会变化，只是未被clip的区域不会被绘制
                if (bitmap != null && isShowIcon)
                    canvas.drawBitmap(bitmap, startDrawTextX - bitmap.getWidth() - imageMarginLeft, baselineCenter - maxTextHeightCenter + 5, paintCenterText);

                canvas.restore();
            } else if (translateY < secondLineY && translateYNext > secondLineY) {
                // 条目经过第二条线
                canvas.save();
                canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                canvas.drawText(as, startDrawTextX, baselineCenter, paintCenterText);
                if (bitmap != null && isShowIcon)
                    canvas.drawBitmap(bitmap, startDrawTextX - bitmap.getWidth() - imageMarginLeft, baselineCenter - maxTextHeightCenter + 5, paintCenterText);

                canvas.restore();
                canvas.save();
                canvas.clipRect(0, secondLineY - translateY, measuredWidth, (int) (itemHeightCenter));
                canvas.drawText(as, startDrawTextX, baselineOuter + (translateYNext - translateY) - itemHeightOuter, paintOuterText);
                if (bitmap != null && isShowIcon)
                    canvas.drawBitmap(bitmap, startDrawTextX - bitmap.getWidth() - imageMarginLeft, baselineOuter - maxTextHeightOuter + 5, paintOuterText);

                canvas.restore();
            } else if (translateY >= firstLineY && translateYNext <= secondLineY) {
                // 中间条目
                canvas.clipRect(0, 0, measuredWidth, (int) (itemHeightCenter));
                canvas.drawText(as, startDrawTextX, baselineCenter, paintCenterText);
                if (bitmap != null && isShowIcon)
                    canvas.drawBitmap(bitmap, startDrawTextX - bitmap.getWidth() - imageMarginLeft, baselineCenter - maxTextHeightCenter + 5, paintCenterText);
            } else {
                // 其他条目
                canvas.clipRect(0, 0, measuredWidth, (int) (itemHeightOuter));
                canvas.drawText(as, startDrawTextX, baselineOuter, paintOuterText);
                if (bitmap != null && isShowIcon)
                    canvas.drawBitmap(bitmap, startDrawTextX - bitmap.getWidth() - imageMarginLeft, baselineOuter - maxTextHeightOuter + 5, paintOuterText);
            }

            if (translateY >= firstLineY && translateY < (firstLineY + secondLineY) / 2
                    || translateYNext > (firstLineY + secondLineY) / 2 && translateYNext <= secondLineY) {
                selectedItem = l1;
            }
            canvas.restore();
            j1++;
            translateY = translateYNext;
            translateYNext = getTranslateY(j1 + 1, j3);
        }
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            int saveCount = canvas.saveLayer(layerTopRect, paintLayerTop);
            canvas.drawRect(layerTopRect, paintLayerTop);
            canvas.restoreToCount(saveCount);

            int saveCount_ = canvas.saveLayer(layerButtomRect, paintLayerButtom);
            canvas.drawRect(layerButtomRect, paintLayerButtom);
            canvas.restoreToCount(saveCount_);
        }
    }

    private int getTranslateY(int j1, int j3) {
        if (totalScrollY >= 0) {
            if (j1 <= (itemsVisible - 1) / 2 + 1) {
                return (int) (itemHeightOuter * j1 - itemHeightOuter - j3);
            } else if (j1 == (itemsVisible - 1) / 2 + 2) {
                return (int) (itemHeightOuter * (itemsVisible - 1) / 2 + itemHeightCenter - j3 * itemHeightCenter / itemHeightOuter);
            } else {
                return (int) (itemHeightOuter * j1 - itemHeightOuter - j3 + (itemHeightCenter - itemHeightOuter));
            }
        } else {
            if (j1 < (itemsVisible - 1) / 2 + 1) {
                return (int) (itemHeightOuter * j1 - itemHeightOuter - j3);
            } else if (j1 == (itemsVisible - 1) / 2 + 1) {
                return (int) (itemHeightOuter * ((itemsVisible - 1) / 2 + 1) - itemHeightOuter - j3 * itemHeightCenter / itemHeightOuter);
            } else {
                return (int) (itemHeightOuter * j1 - itemHeightOuter - j3 + (itemHeightCenter - itemHeightOuter));
            }
        }

    }


    /**
     * 绘制文字起始位置
     */
    private int getTextX(String a, Paint paint, Rect rect) {
        paint.getTextBounds(a, 0, a.length(), rect);
        // 获取到的是实际文字宽度
        int textWidth = rect.width();
        textWidth = (int) paint.measureText(a);
        // 转换成绘制文字宽度
        textWidth *= scaleX;
        if (wheelGravity == WHEEL_RIGHT) {
            return maxTextWidth / 2 - textWidth / 2 + (measuredWidth - maxTextWidth);
        } else if (wheelGravity == WHEEL_LEFT) {
            return maxTextWidth / 2 - textWidth / 2;
        } else {
            return (measuredWidth - textWidth) / 2;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        remeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                cancelFuture();
                previousY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                float dy = previousY - event.getRawY();
                previousY = event.getRawY();

                totalScrollY = (int) (totalScrollY + dy);

                // 边界处理。
                if (!isLoop) {
                    if (totalScrollY < top) {
                        totalScrollY = (int) top;
                    } else if (totalScrollY > bottom) {
                        totalScrollY = (int) bottom;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            default:
                if (!eventConsumed) {
                    float y = event.getY();
                    //顶部到点击点的弧长
                    double l = Math.acos((radius - y) / radius) * radius;
                    //点击的item在可见item的index
                    int circlePosition = (int) ((l + itemHeightCenter / 2) / itemHeightCenter);

                    float extraOffset = (totalScrollY % itemHeightOuter + itemHeightOuter) % itemHeightOuter;// 负数时取正数，比如-8时+10取2
                    mOffset = (int) ((circlePosition - itemsVisible / 2) * itemHeightCenter - extraOffset);

                    //点击的item在可见item的index
                    int pos;
                    if (y <= firstLineY) {
                        pos = (int) (y / itemHeightOuter);
                    } else if (y >= secondLineY) {
                        pos = (int) ((int) (y - itemHeightCenter) / itemHeightOuter + 1);
                        if (pos > itemsVisible - 1) {
                            pos = itemsVisible - 1;
                        }
                    } else {
                        pos = itemsVisible / 2;
                    }

                    mOffset = (int) ((pos - itemsVisible / 2) * itemHeightOuter - extraOffset);

                    if (!isLoop) {
                        if (totalScrollY + mOffset > bottom) {
                            mOffset = (int) (bottom - totalScrollY);
                        }

                        if (totalScrollY + mOffset < top) {
                            mOffset = (int) (top - totalScrollY);
                        }
                    }

                    //松手才会调用
                    if ((System.currentTimeMillis() - startTime) > 120) {
                        // 处理拖拽松手事件
                        smoothScroll(ACTION.DAGGLE);
                    } else {
                        // 处理条目点击事件
                        smoothScroll(ACTION.CLICK);
                    }
                }
                break;
        }

        invalidate();
        return true;
    }


    public interface OnItemSelectedListener {
        void onItemSelected(int selectedIndex, String key, String item);
    }
}
