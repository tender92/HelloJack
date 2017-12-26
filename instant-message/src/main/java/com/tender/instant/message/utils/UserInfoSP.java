package com.tender.instant.message.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by boyu on 2017/12/15.
 */

public class UserInfoSP {
    private static final String SP_NAME = "user_sp";
    private static UserInfoSP instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;

    private UserInfoSP(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static UserInfoSP getInstance(Context context) {
        if (instance == null) {
            instance = new UserInfoSP(context);
        }
        return instance;
    }

    public void putString(String key, String value) {
        if (key == null) {
            return;
        }
        editor.putString(key, value).commit();
    }

    public String getString(String key, String defaultKey) {
        return sp.getString(key, defaultKey);
    }

    public void remove(String key) {
        editor.remove(key).commit();
    }
}
