package com.tender.hellojack.data.local;

import com.tender.hellojack.data.IResource;

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
}
