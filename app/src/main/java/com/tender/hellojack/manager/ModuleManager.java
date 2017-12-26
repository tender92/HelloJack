package com.tender.hellojack.manager;

import android.app.Application;
import android.support.annotation.NonNull;

import com.tender.instant.message.manager.IMModule;
import com.tender.lbs.manager.LBSModuleManager;
import com.tender.message.push.manager.MessagePushModule;
import com.tender.speech.SpeechModule;
import com.tender.tools.manager.CTModule;
import com.tender.tools.manager.IModuleManager;
import com.tender.umengshare.manager.StatisticsModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boyu on 2017/11/10.
 */

public class ModuleManager implements IModuleManager {

    private final List<IModuleManager> moduleList;

    public ModuleManager(@NonNull Application application) {
        moduleList = new ArrayList<>();
        moduleList.add(new IMModule(application));
        moduleList.add(new CTModule(application));
        moduleList.add(new SpeechModule(application));
        moduleList.add(new LBSModuleManager(application));
        moduleList.add(new MessagePushModule(application));
        moduleList.add(new StatisticsModule(application));
    }

    @Override
    public void onInit() {
        for (IModuleManager module : moduleList) {
            module.onInit();
        }
    }

    @Override
    public void onTerminate() {
        for (IModuleManager module : moduleList) {
            module.onTerminate();
        }
    }
}
