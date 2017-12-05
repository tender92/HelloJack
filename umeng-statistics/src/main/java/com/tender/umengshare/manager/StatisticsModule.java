package com.tender.umengshare.manager;

import android.app.Application;

import com.tender.tools.manager.IModuleManager;

/**
 * Created by boyu on 2017/11/10.
 */

public class StatisticsModule implements IModuleManager {

    private Application application;

    public StatisticsModule(Application application) {
        this.application = application;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onTerminate() {

    }
}
