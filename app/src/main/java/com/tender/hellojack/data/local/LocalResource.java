package com.tender.hellojack.data.local;

import com.tender.hellojack.data.IResource;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.model.enums.GenderEnum;
import com.tender.tools.manager.PrefManager;

import rx.Observable;

/**
 * Created by boyu on 2017/12/7.
 */

public class LocalResource implements IResource {
    private static LocalResource instance;

    public static LocalResource getInstance() {
        if (instance == null) {
            instance = new LocalResource();
        }
        return instance;
    }

    @Override
    public Observable<Object> login(String userName, String token) {
        return null;
    }

    @Override
    public Observable<Object> register(String account, String name, String pwd) {
        return null;
    }

    @Override
    public Observable<UserInfo> getUserInfo(String account) {
        UserInfo userInfo = new UserInfo();
        userInfo.account = "xiaoming";
        userInfo.displayName = "大锤子哥";
        userInfo.address = "上海市浦东新区张衡路";
        userInfo.avatar = PrefManager.getUserHeaderPath();
        userInfo.birthDay = "1996-03-08";
        userInfo.email = "hdlgdx987@163.com";
        userInfo.gender = GenderEnum.MALE;
        userInfo.mobile = "13888888888";
        userInfo.name = "小明";
        userInfo.region = "上海市";
        userInfo.signature = "小明不迟到";
        return Observable.just(userInfo);
    }
}
