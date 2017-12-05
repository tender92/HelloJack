package com.tender.hellojack.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.tender.hellojack.manager.MyApplication;
import com.tender.tools.utils.DisplayUtil;

/**
 * Created by boyu on 17/1/20.
 */

public class App {
    private static final Application instance;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null)
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
        } catch (final Exception e) {
            Log.e("App", "Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                Log.e("App", "Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            instance = app;
        }
    }

    /**
     * 读取application 节点 meta-data 信息
     *
     * @return values
     */
    private static String getMetaData(String key) {
        try {
            ApplicationInfo appInfo = instance.getPackageManager()
                    .getApplicationInfo(instance.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static PackageInfo getPackageInfo() {
        try {
            PackageInfo packageInfo = instance.getPackageManager()
                    .getPackageInfo(App.instance.getPackageName(), 0);
            return packageInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取app上下文
     *
     * @return
     */
    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    /**
     * 安全的执行一个任务
     * @param task
     */
    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();
        // 如果当前线程是主线程
        if (curThreadId == MyApplication.mMainThreadId) {
            task.run();
        } else {
            // 如果当前线程不是主线程
            MyApplication.mHandler.post(task);
        }
    }

    /**
     * 通过res id获取string值
     * @param resId
     * @return
     */
    public static String getStringByResId(int resId) {
        return instance.getResources().getString(resId);
    }

    /**
     * 通过res id获取Color值
     * @param resId
     * @return
     */
    public static int getColorByResId(int resId) {
        return instance.getResources().getColor(resId);
    }

    /**
     * 通过res id获取dimension值
     * @param resId
     * @return
     */
    public static float getDimensionByResId(int resId){
        return instance.getResources().getDimension(resId);
    }

    public static Drawable getDrawableByResId(int resId){
        return instance.getResources().getDrawable(resId);
    }

    /**
     * 获取屏幕像素矩阵
     * @return
     */
    public static DisplayMetrics getScreenMetrics(){
        return instance.getResources().getDisplayMetrics();
    }

    public static int getScreenWidth(){
        return getScreenMetrics().widthPixels - 2 * DisplayUtil.dip2px(getAppContext(), 20);
    }

    public static int getScreenHeight(){
        return getScreenMetrics().heightPixels;
    }
}
