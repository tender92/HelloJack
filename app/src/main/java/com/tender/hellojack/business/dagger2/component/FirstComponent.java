package com.tender.hellojack.business.dagger2.component;

import com.tender.hellojack.business.dagger2.activity.DFirstActivity;
import com.tender.hellojack.business.dagger2.module.FirstModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by boyu on 2018/3/23.
 * Module中提供统一Cloth单例
 *
 */

@Singleton
@Component(modules = FirstModule.class)
public interface FirstComponent {
    void inject(DFirstActivity dFirstActivity);
}
