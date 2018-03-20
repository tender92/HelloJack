package com.tender.hellojack.utils;

import com.litesuits.orm.LiteOrm;
import com.tender.hellojack.BuildConfig;
import com.tender.hellojack.business.translate.service.SingleRequestService;
import com.tender.hellojack.business.translate.service.TranslateApiProvider;
import com.tender.hellojack.business.translate.service.WrapApiService;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.LocalResource;
import com.tender.hellojack.data.remote.RemoteResource;
import com.tender.tools.utils.ui.UIUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by boyu on 2017/12/7.
 */

public class Injection {
    public static ResourceRepository provideRepository() {
       return ResourceRepository.getInstance(LocalResource.getInstance(), RemoteResource.getInstance());
    }
    public static ScheduleProvider provideSchedule() {
        return ScheduleProvider.getInstance();
    }

    public static LiteOrm provideLiteOrm() {
        LiteOrm liteOrm = LiteOrm.newSingleInstance(UIUtil.getAppContext(), "GdTranslate.db");
        liteOrm.setDebugged(BuildConfig.DEBUG);
        return liteOrm;
    }

    public static WrapApiService provideWrapApiService() {
        return new WrapApiService(TranslateApiProvider.provideApiBaiDu(),
                TranslateApiProvider.provideApiGoogle(),
                TranslateApiProvider.provideApiJinShan(),
                TranslateApiProvider.provideApiYouDao());
    }

    public static SingleRequestService provideRequestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SingleRequestService.class);
    }
}
