package com.tender.hellojack.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.tender.tools.utils.string.PinyinUtils;

import java.io.Serializable;

/**
 * Created by boyu on 2017/12/24.
 */

public class Contact implements Comparable<Contact>, Serializable {

    public String mAccount;//账号
    public String mDisplayName;//要显示的名字（没有备注的话就显示昵称）
    public String mName;//昵称
    public String mAlias;//备注
    public String mPinyin;//昵称/备注的全拼
    private Friend mFriend;//你的好友信息
    public UserInfo mUserInfo;//好友自己的信息
    public String mAvatar;//头像地址

    public Contact(Friend mFriend, UserInfo mUserInfo) {
        this.mFriend = mFriend;
        this.mUserInfo = mUserInfo;
        fit();
    }

    public Contact(String mAccount) {
        this.mAccount = mAccount;
        fit();
    }

    private void fit() {
        this.mAccount = mUserInfo.account;
        this.mName = mUserInfo.name;
        if (mFriend != null)
            this.mAlias = mFriend.alias;
        this.mAvatar = mUserInfo.avatar;
        this.mDisplayName = TextUtils.isEmpty(mAlias) ? mName : mAlias;
        this.mPinyin = PinyinUtils.getPinyin(mDisplayName);
    }

    @Override
    public int compareTo(@NonNull Contact o) {
        return this.mPinyin.compareTo(o.mPinyin);
    }
}
