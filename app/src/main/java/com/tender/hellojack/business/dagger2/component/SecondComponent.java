package com.tender.hellojack.business.dagger2.component;

import com.tender.hellojack.business.dagger2.activity.DSecondActivity;
import com.tender.hellojack.business.dagger2.module.SecondModule;
import com.tender.hellojack.business.dagger2.scope.PerActivity;

import dagger.Component;

/**
 * Created by boyu on 2018/3/23.
 * @PerActivity 约束Module提供实例的范围
 * 依赖BaseComponent，可调用BaseModule提供的实例
 */

@PerActivity
@Component(modules = SecondModule.class, dependencies = BaseComponent.class)
public interface SecondComponent {
    void inject(DSecondActivity dSecondActivity);
}
