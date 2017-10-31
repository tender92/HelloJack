package com.tender.umengshare;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by boyu on 2017/10/23.
 */

public class DataShareManager {

    private static DataShareManager instance;
    private static boolean isShare = false;

    private DataShareManager() {}

    public static DataShareManager getInstance(boolean shareFunActive) {
        if (instance == null) {
            instance = new DataShareManager();
        }
        return instance;
    }

    public void init(boolean shareFunActive) {
        isShare = shareFunActive;
    }

    /**
     * session的统计
     * activity中onResume方法中调用
     * @param context
     */
    public void onResume(Context context) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onResume(context);
    }

    /**
     * session的统计
     * activity中onPause方法中调用
     * @param context
     */
    public void onPause(Context context) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onPause(context);
    }

    /**
     *  账号的统计
     * @param ID
     */
    public void onProfileSignIn(String ID) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onProfileSignIn(ID);
    }

    /**
     *  账号的统计
     * @param provider
     * @param ID
     */
    public void onProfileSignIn(String provider, String ID) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onProfileSignIn(provider, ID);
    }

    /**
     * 账号的统计
     */
    public void onProfileSignOff() {
        if (!isShareFunActive())
            return;
        MobclickAgent.onProfileSignOff();
    }

    /**
     * 统计页面跳转
     * activity、fragment中onResume方法中调用
     * @param pageName
     */
    public void onPageStart(String pageName) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onPageStart(pageName);
    }

    /**
     * 统计页面跳转
     * activity、fragment中onPause方法中调用
     * @param pageName
     */
    public void onPageEnd(String pageName) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onPageEnd(pageName);
    }

    /**
     * 在程序入口处的 Activity  中调用
     * 友盟统计sdk 6.0.0版本及以后使用
     * @param enable
     */
    public void enableEncrypt(boolean enable) {
        if (!isShareFunActive())
            return;
        MobclickAgent.enableEncrypt(enable);
    }

    /**
     *  统计事件发生次数
     * @param context
     * @param eventId
     */
    public void onEvent(Context context, String eventId) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onEvent(context, eventId);
    }

    /**
     * 统计事件各属性被触发的次数
     * @param context
     * @param eventId 事件ID
     * @param map 当前事件的属性和取值
     */
    public void onEvent(Context context, String eventId, HashMap map) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onEvent(context, eventId, map);
    }

    /**
     * 统计数值型变量的值的分布
     * @param context
     * @param id 事件ID
     * @param m 当前事件的属性和取值
     * @param du 当前事件的数值，取值范围是-2,147,483,648 到 +2,147,483,647 之间的有符号整数，
     *           即int 32类型，如果数据超出了该范围，会造成数据丢包，影响数据统计的准确性。
     */
    public void onEventValue(Context context, String id, Map<String,String> m, int du) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onEventValue(context, id, m, du);
    }

    /**
     * 错误统计功能
     * among SDK通过Thread.UncaughtExceptionHandler 捕获程序崩溃日志，并在程序下次启动时发送到服务器
     * 如不需要错误统计功能，可调用此方法设置为false
     * @param enable
     */
    public void setCatchUncaughtExceptions(boolean enable) {
        if (!isShareFunActive())
            return;
        MobclickAgent.setCatchUncaughtExceptions(enable);
    }

    /**
     * 错误统计
     * @param context
     * @param error
     */
    public void reportError(Context context, String error) {
        if (!isShareFunActive())
            return;
        MobclickAgent.reportError(context, error);
    }

    /**
     * 错误统计
     * @param context
     * @param e
     */
    public void reportError(Context context, Throwable e) {
        if (!isShareFunActive())
            return;
        MobclickAgent.reportError(context, e);
    }

    /**
     * 调用Process.kill或者System.exit之类的方法杀死进程之前，
     * 调用此方法来保存统计数据
     * @param context
     */
    public void onKillProcess(Context context) {
        if (!isShareFunActive())
            return;
        MobclickAgent.onKillProcess(context);
    }

    /**
     * 调试模式
     * 如果使用调试模式，请在程序入口处调用，设置为true
     * @param enable
     */
    public void setDebugMode(boolean enable) {
        if (!isShareFunActive())
            return;
        MobclickAgent.setDebugMode(enable);
    }

    private boolean isShareFunActive() {
        return isShare;
    }
}
