package com.tender.message.push.manager;

import android.app.Application;

import com.tender.tools.manager.IModuleManager;

/**
 * Created by boyu on 2017/11/10.
 */

public class MessagePushModule implements IModuleManager {

    private Application application;

    public MessagePushModule(Application application) {
        this.application = application;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onTerminate() {

    }
}
