package com.tender.hellojack.business.translate.service;

import com.tender.hellojack.business.translate.result.YouDaoResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by boyu on 2018/2/2.
 */

public interface ApiYouDao {
    @GET("api?")
    Observable<YouDaoResult> translateYouDao(
            @Query("q") String q,
            @Query("from") String from,
            @Query("to") String to,
            @Query("appKey") String appKey,
            @Query("salt") String salt,
            @Query("sign") String sign
    );
}
