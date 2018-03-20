package com.tender.hellojack.business.translate.service;

import com.tender.hellojack.business.translate.result.BaiDuResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by boyu on 2018/1/31.
 */

public interface ApiBaiDu {
    @GET("api/trans/vip/translate?")
    Observable<BaiDuResult> translateBaiDu(
            @Query("q") String q,
            @Query("from")String from,
            @Query("to")String to,
            @Query("appid")String appid,
            @Query("salt")String salt,
            @Query("sign")String sign
    );
}
