package com.tender.hellojack.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.UUID;

/**
 * Created by boyu on 2017/10/19.
 */

public class PrefManager implements IManager {

    private static PrefManager instance;

    private SharedPreferences pre;

    private static final String USER_HEADER_PATH = "user_header_path";
    private static final String USER_ACCOUNT = "user_account";
    private static final String USER_NAME = "user_name";
    private static final String USER_GENDER = "user_gender";
    private static String SCREEN_WIDTH = "screen_width";
    private static String SCREEN_HEIGHT = "screen_height";

    private PrefManager() {
    }

    public static PrefManager getInstance() {
        if (instance == null) {
            instance = new PrefManager();
        }

        return instance;
    }

    @Override
    public void init(Context context) {
        pre = PreferenceManager.getDefaultSharedPreferences(context);

        String uuid = UUID.randomUUID().toString();
        String account = "user" + uuid.substring(uuid.length() - 6);
        instance.setUserAccount(account);
        instance.setUserName("Tender");
        instance.setUserGender(1);
    }

    @Override
    public void destroy() {

    }

    public static String getUserHeaderPath() {
        return instance.pre.getString(USER_HEADER_PATH, "");
    }

    public static void setUserHeaderPath(String path) {
        instance.pre.edit().putString(USER_HEADER_PATH, path).commit();
    }

    public static String getUserAccount() {
        return instance.pre.getString(USER_ACCOUNT, "");
    }
    public static void setUserAccount(String account) {
        instance.pre.edit().putString(USER_ACCOUNT, account).commit();
    }
    public static String getUserName() {
        return instance.pre.getString(USER_NAME, "");
    }
    public static void setUserName(String name) {
        instance.pre.edit().putString(USER_NAME, name).commit();
    }
    public static int getUserGender() {
        return instance.pre.getInt(USER_GENDER, 0);
    }
    public static void setUserGender(int gender) {
        instance.pre.edit().putInt(USER_GENDER, gender).commit();
    }

    public static void saveScreenWidth(int width) {
        instance.pre.edit().putInt(SCREEN_WIDTH, width).commit();
    }
    public static int getScreenWidth() {
        return instance.pre.getInt(SCREEN_WIDTH, 0);
    }
    public static void saveScreenHeight(int height) {
        instance.pre.edit().putInt(SCREEN_HEIGHT, height).commit();
    }
    public static int getScreenHeight() {
        return instance.pre.getInt(SCREEN_HEIGHT, 0);
    }
}
