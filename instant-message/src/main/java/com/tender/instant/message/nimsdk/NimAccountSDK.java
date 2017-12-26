package com.tender.instant.message.nimsdk;

import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tender.instant.message.UserInfoConst;
import com.tender.instant.message.utils.UserInfoSP;
import com.tender.tools.utils.UIUtil;

/**
 * Created by boyu on 2017/12/15.
 */

public class NimAccountSDK {
    private static String account;
    private static String token;

    public static void login(String userName, String token, RequestCallback<LoginInfo> callback) {
        LoginInfo info = new LoginInfo(userName, token);
        AbortableFuture<LoginInfo> loginRequest = NIMClient.getService(AuthService.class).login(info);
        loginRequest.setCallback(callback);
    }

    public static String getUserAccount() {
        account = UserInfoSP.getInstance(UIUtil.getAppContext()).getString(UserInfoConst.Account.KEY_USER_ACCOUNT, "");
        return account;
    }

    public static String getUserToken() {
        token = UserInfoSP.getInstance(UIUtil.getAppContext()).getString(UserInfoConst.Account.KEY_USER_TOKEN, "");
        return token;
    }

    public static void saveUserAccount(String account) {
        NimAccountSDK.account = account;
        UserInfoSP.getInstance(UIUtil.getAppContext()).putString(UserInfoConst.Account.KEY_USER_ACCOUNT, account);
    }

    public static void saveUserToken(String token) {
        NimAccountSDK.token = token;
        UserInfoSP.getInstance(UIUtil.getAppContext()).putString(UserInfoConst.Account.KEY_USER_TOKEN, token);
    }
}
