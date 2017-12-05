package com.tender.tools.manager;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Created by boyu on 2017/11/10.
 */

public class CommonToolsModule implements IModuleManager {

    private Application application;

    public CommonToolsModule(@NonNull Application application) {
        this.application = application;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onTerminate() {

    }
}
