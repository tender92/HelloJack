package com.tender.hellojack.data;

import com.tender.hellojack.data.local.LocalResource;
import com.tender.hellojack.data.remote.RemoteResource;

import rx.Observable;

/**
 * Created by boyu on 2017/12/7.
 */

public class ResourceRepository implements IResource {
    private static ResourceRepository instance;
    private final LocalResource localResource;
    private final RemoteResource remoteResource;

    private ResourceRepository(LocalResource localResource, RemoteResource remoteResource) {
        this.localResource = localResource;
        this.remoteResource = remoteResource;
    }

    synchronized public static ResourceRepository getInstance(LocalResource localResource, RemoteResource remoteResource) {
        if (instance == null) {
            instance = new ResourceRepository(localResource, remoteResource);
        }
        return instance;
    }

    @Override
    public Observable<Object> login(String userName, String token) {
        return remoteResource.login(userName, token);
    }

    @Override
    public Observable<Object> register(String account, String name, String pwd) {
        return remoteResource.register(account, name, pwd);
    }
}
