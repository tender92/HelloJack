package com.tender.tools.utils.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.tender.tools.manager.CTModule;

/**
 * Created by boyu on 2017/12/15.
 */

public class UIUtil {
    public static Context getAppContext() {
        return CTModule.getAppContext();
    }

    public static DisplayMetrics getScreenMetrics(){
        return getAppContext().getResources().getDisplayMetrics();
    }

    public static int getScreenWidth(){
        return getScreenMetrics().widthPixels - 2 * DisplayUtil.dip2px(getAppContext(), 20);
    }

    public static int getScreenHeight(){
        return getScreenMetrics().heightPixels;
    }

    /**
     * 安全的执行一个任务
     * @param task
     */
    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();
        // 如果当前线程是主线程
        if (curThreadId == CTModule.mMainThreadId) {
            task.run();
        } else {
            // 如果当前线程不是主线程
            CTModule.mHandler.post(task);
        }
    }

    public static void postTaskDelay(Runnable task, int delayMillis) {
        CTModule.mHandler.postDelayed(task, delayMillis);
    }

    public static Handler getMainHandle() {
        return CTModule.mHandler;
    }

    /**
     * 通过res id获取string值
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getAppContext().getResources().getString(resId);
    }

    /**
     * 通过res id获取Color值
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        return getAppContext().getResources().getColor(resId);
    }

    /**
     * 通过res id获取dimension值
     * @param resId
     * @return
     */
    public static float getDimension(int resId){
        return getAppContext().getResources().getDimension(resId);
    }

    public static Drawable getDrawable(int resId){
        return getAppContext().getResources().getDrawable(resId);
    }

    public static void showToast(String content) {
        DialogUtil.showHint(getAppContext(), content);
    }
}
