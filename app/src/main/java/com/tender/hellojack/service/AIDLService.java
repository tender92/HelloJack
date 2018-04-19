package com.tender.hellojack.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.tender.hellojack.IMyAidlInterface;
import com.tender.hellojack.User;

/**
 * Created by boyu on 2018/4/19.
 */

public class AIDLService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    private class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getUserName() throws RemoteException {
            return "Tender";
        }

        @Override
        public User getUser() throws RemoteException {
            User user = new User();
            user.setUserName("User Tender");
            return user;
        }
    }
}
