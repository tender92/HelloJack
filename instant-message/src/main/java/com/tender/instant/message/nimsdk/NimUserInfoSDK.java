package com.tender.instant.message.nimsdk;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

/**
 * Created by boyu on 2017/12/17.
 */

public class NimUserInfoSDK {
    /**
     * 获取服务器用户资料(常用于实时要求高的场景)
     * <p>
     * 从服务器获取用户资料，一般在本地用户资料不存在时调用，获取后 SDK 会负责更新本地数据库
     */
    public static void getUserInfoFormServer(List<String> accounts, RequestCallback<List<NimUserInfo>> callback) {
        NIMClient.getService(UserService.class).fetchUserInfo(accounts)
                .setCallback(callback);
    }
}
