package com.tender.hellojack.business.dagger2.module;

import com.tender.hellojack.business.dagger2.bean.Cloth;
import com.tender.hellojack.business.dagger2.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by boyu on 2018/3/23.
 */

@Module
public class ThirdModule {

    @PerActivity
    @Provides
    public Cloth getYellowCloth() {
        Cloth yellowCloth = new Cloth();
        yellowCloth.setColor("黄色");
        return yellowCloth;
    }
}
