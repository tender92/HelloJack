package com.tender.hellojack.business.dagger2.component;

import com.tender.hellojack.business.dagger2.activity.DFourthActivity;
import com.tender.hellojack.business.dagger2.module.FourModule;
import com.tender.hellojack.business.dagger2.scope.PerActivity;

import dagger.Subcomponent;

/**
 * Created by boyu on 2018/3/23.
 * @Subcomponent 子组件能获得父组件提供的实例
 */

@PerActivity
@Subcomponent(modules = FourModule.class)
public interface SubFourComponent {
    void inject(DFourthActivity dFourthActivity);
}
