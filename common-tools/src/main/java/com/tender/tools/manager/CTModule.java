package com.tender.tools.manager;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * Created by boyu on 2017/11/10.
 */

public class CTModule implements IModuleManager {

    private static Application application;

    public static Thread mMainThread;//主线程
    public static long mMainThreadId;//主线程id
    public static Looper mMainLooper;//循环队列
    public static Handler mHandler;//主线程Handler

    public CTModule(@NonNull Application application) {
        this.application = application;
    }

    public static Context getAppContext() {
        return application.getApplicationContext();
    }

    @Override
    public void onInit() {
        mMainThread = Thread.currentThread();
        mMainThreadId = mMainThread.getId();
        mMainLooper = application.getMainLooper();
        mHandler = new Handler();

        PrefManager prefManager = PrefManager.getInstance();
        prefManager.init(getAppContext());
    }

    @Override
    public void onTerminate() {

    }
}
