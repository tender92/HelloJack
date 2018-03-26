package com.tender.hellojack.business.dagger2.module;

import com.tender.hellojack.business.dagger2.bean.Cloth;

import dagger.Module;
import dagger.Provides;

/**
 * Created by boyu on 2018/3/23.
 */

@Module
public class FourModule {
    @Provides
    public Cloth getBlackCloth() {
        Cloth blackCloth = new Cloth();
        blackCloth.setColor("黑色");
        return blackCloth;
    }
}
