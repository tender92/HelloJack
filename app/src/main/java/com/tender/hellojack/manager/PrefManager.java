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
}
