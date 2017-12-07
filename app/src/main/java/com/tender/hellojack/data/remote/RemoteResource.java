package com.tender.hellojack.data.remote;

import com.tender.hellojack.data.IResource;

/**
 * Created by boyu on 2017/12/7.
 */

public class RemoteResource implements IResource {
    private static RemoteResource instance;

    public static RemoteResource getInstance() {
        if (instance == null) {
            instance = new RemoteResource();
        }
        return instance;
    }
}
