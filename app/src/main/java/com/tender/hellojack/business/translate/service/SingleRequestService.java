package com.tender.hellojack.business.translate.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by boyu on 2018/3/6.
 */

public interface SingleRequestService {
    @GET
    Call<ResponseBody> downloadSoundFile(@Url String soundUrl);
}
