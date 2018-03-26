package com.tender.hellojack.business.dagger2.module;

import com.tender.hellojack.business.dagger2.bean.ClothHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by boyu on 2018/3/23.
 */

@Module
public class BaseModule {

    @Provides
    @Singleton
    public ClothHandler getClothHandler() {
        return new ClothHandler();
    }
}
