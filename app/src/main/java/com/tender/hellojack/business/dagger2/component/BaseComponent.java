package com.tender.hellojack.business.dagger2.component;

import com.tender.hellojack.business.dagger2.bean.ClothHandler;
import com.tender.hellojack.business.dagger2.module.BaseModule;
import com.tender.hellojack.business.dagger2.module.FourModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by boyu on 2018/3/23.
 * 为多个Activity提供handler单例，故放在application中
 */

@Singleton
@Component(modules = BaseModule.class)
public interface BaseComponent {
    ClothHandler getClothHandler();

    SubFourComponent getSubFourComponent(FourModule fourModule);
}
