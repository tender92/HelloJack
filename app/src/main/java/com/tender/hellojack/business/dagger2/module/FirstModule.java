package com.tender.hellojack.business.dagger2.module;

import com.tender.hellojack.business.dagger2.bean.Cloth;
import com.tender.hellojack.business.dagger2.bean.Cloths;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by boyu on 2018/3/23.
 * @Name 提供指定的实例
 */

@Module
public class FirstModule {

    @Singleton
    @Provides
    public Cloth getCloth() {
        Cloth redCloth = new Cloth();
        redCloth.setColor("红色");
        return redCloth;
    }

    @Provides
    @Named("red")
    public Cloths getRedCloths(Cloth cloth) {
        return new Cloths(cloth);
    }

    @Provides
    @Named("blue")
    public Cloths getBlueCloths() {
        Cloth blueCloth = new Cloth();
        blueCloth.setColor("蓝色");
        return new Cloths(blueCloth);
    }
}
