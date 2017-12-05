package com.tender.speech;

import android.app.Application;
import android.support.annotation.NonNull;

import com.tender.tools.manager.IModuleManager;

/**
 * Created by boyu on 2017/11/10.
 */

public class SpeechModule implements IModuleManager {

    private Application application;

    public SpeechModule(@NonNull Application application) {
        this.application = application;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onTerminate() {

    }
}
