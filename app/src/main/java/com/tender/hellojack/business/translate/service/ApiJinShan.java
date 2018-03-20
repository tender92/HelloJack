package com.tender.hellojack.business.translate.service;

import com.tender.hellojack.business.translate.result.JinShanResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by boyu on 2018/2/2.
 */

public interface ApiJinShan {
    @GET("api/dictionary.php?")
    Observable<JinShanResult> translateJinShan(
            @Query("w") String q,
            @Query("key") String key,
            @Query("type") String type
    );
}
