package com.tender.instant.message.manager;

import android.app.Application;

import com.tender.tools.manager.IModuleManager;

/**
 * Created by boyu on 2017/12/15.
 */

public class IMModule implements IModuleManager {
    private static Application app;

    public IMModule(Application application) {
        app = application;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onTerminate() {

    }
}
