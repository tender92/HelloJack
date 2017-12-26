package com.tender.instant.message.model;

/**
 * Created by boyu on 2017/12/15.
 * 用户信息缓存
 */

public class UserCache {
    private static String account;

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        UserCache.account = account;
    }
}
