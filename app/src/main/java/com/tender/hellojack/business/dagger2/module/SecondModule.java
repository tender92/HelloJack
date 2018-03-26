package com.tender.hellojack.business.dagger2.module;

import com.tender.hellojack.business.dagger2.bean.Cloth;
import com.tender.hellojack.business.dagger2.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by boyu on 2018/3/23.
 */

@Module
public class SecondModule {
    @PerActivity
    @Provides
    public Cloth getGreenCloth() {
        Cloth greenCloth = new Cloth();
        greenCloth.setColor("绿色");
        return greenCloth;
    }
}
