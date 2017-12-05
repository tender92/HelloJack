package com.tender.lbs.manager;

import android.app.Application;
import android.support.annotation.NonNull;

import com.tender.tools.manager.IModuleManager;

/**
 * Created by boyu on 2017/11/10.
 */

public class LBSModuleManager implements IModuleManager {

    private Application application;

    public LBSModuleManager(@NonNull Application application) {
        this.application = application;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onTerminate() {

    }
}
