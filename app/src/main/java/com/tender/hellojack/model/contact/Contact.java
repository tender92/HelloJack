package com.tender.hellojack.model.contact;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tender.tools.utils.string.PinyinUtils;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by boyu on 2017/12/24.
 */

public class Contact extends RealmObject implements Comparable<Contact>, Serializable {

    @PrimaryKey
    private String mAccount;//账号
    private String mDisplayName;//要显示的名字（没有备注的话就显示昵称）
    private String mName;//昵称
    private String mAlias;//备注
    private String mPinyin;//昵称/备注的全拼
    private String mAvatar;//头像地址
    private long createTime;

    public Contact() {}

    public Contact(Friend mFriend, UserInfo mUserInfo) {
        this.mAccount = mUserInfo.getAccount();
        this.mName = mUserInfo.getName();
        if (mFriend != null)
            this.mAlias = mFriend.getAlias();
        this.mAvatar = mUserInfo.getAvatar();
        this.mDisplayName = TextUtils.isEmpty(mAlias) ? mName : mAlias;
        this.mPinyin = PinyinUtils.getPinyin(mDisplayName);
    }

    public String getmAccount() {
        return mAccount;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public String getmDisplayName() {
        return mDisplayName;
    }

    public void setmDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAlias() {
        return mAlias;
    }

    public void setmAlias(String mAlias) {
        this.mAlias = mAlias;
    }

    public String getmPinyin() {
        return mPinyin;
    }

    public void setmPinyin(String mPinyin) {
        this.mPinyin = mPinyin;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public int compareTo(@NonNull Contact contact) {
        return this.mPinyin.compareTo(contact.getmPinyin());
    }
}
