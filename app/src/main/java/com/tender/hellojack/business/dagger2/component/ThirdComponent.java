package com.tender.hellojack.business.dagger2.component;

import com.tender.hellojack.business.dagger2.activity.DThirdActivity;
import com.tender.hellojack.business.dagger2.module.ThirdModule;
import com.tender.hellojack.business.dagger2.scope.PerActivity;

import dagger.Component;

/**
 * Created by boyu on 2018/3/23.
 */

@PerActivity
@Component(modules = ThirdModule.class, dependencies = BaseComponent.class)
public interface ThirdComponent {
    void inject(DThirdActivity dThirdActivity);
}
