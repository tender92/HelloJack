package com.tender.hellojack.business.translate.service;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.tender.hellojack.BuildConfig;
import com.tender.hellojack.model.translate.ETranslateFrom;
import com.tender.tools.utils.ui.UIUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by boyu on 2018/1/31.
 */

public class TranslateApiProvider {
    public static ApiBaiDu provideApiBaiDu() {
        return createService(ETranslateFrom.BAI_DU);
    }
    public static ApiGoogle provideApiGoogle() {
        return createService(ETranslateFrom.GOOGLE);
    }
    public static ApiJinShan provideApiJinShan() {
        return createService(ETranslateFrom.JIN_SHAN);
    }
    public static ApiYouDao provideApiYouDao() {
        return createService(ETranslateFrom.YOU_DAO);
    }

    private static  <S> S createService(ETranslateFrom type) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HttpUrl.parse(type.getUrl()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        builder.client(provideOkHttpClient());
        return (S) builder.build().create(type.getAqiClass());
    }

    private static OkHttpClient provideOkHttpClient() {
        Cache cache = new Cache(UIUtil.getAppContext().getCacheDir(), 10240 * 1024);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        builder.addNetworkInterceptor(new CacheInterceptor())
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        return builder.build();
    }

    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //cache for 30 days
                    .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
                    .build();
        }
    }
}
