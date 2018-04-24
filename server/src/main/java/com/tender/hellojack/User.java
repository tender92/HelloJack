package com.tender.hellojack;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by boyu on 2018/4/19.
 */

public class User implements Parcelable {
    private String userName;

    public User() {
    }

    protected User(Parcel in) {
        userName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "[User] userName:" + this.getUserName();
    }
}
