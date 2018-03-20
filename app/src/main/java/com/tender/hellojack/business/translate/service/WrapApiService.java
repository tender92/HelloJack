package com.tender.hellojack.business.translate.service;

import com.tender.hellojack.BuildConfig;
import com.tender.hellojack.business.translate.result.AbResult;
import com.tender.hellojack.business.translate.result.BaiDuResult;
import com.tender.hellojack.business.translate.result.GoogleResult;
import com.tender.hellojack.business.translate.result.JinShanResult;
import com.tender.hellojack.business.translate.result.YouDaoResult;
import com.tender.hellojack.model.translate.ETranslateFrom;
import com.tender.tools.TenderLog;
import com.tender.tools.utils.string.TranslateSignUtil;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by boyu on 2018/1/31.
 */

public class WrapApiService {
    private ApiBaiDu mApiBaiDu;
    private ApiGoogle mApiGoogle;
    private ApiJinShan mApiJinShan;
    private ApiYouDao mApiYouDao;

    public WrapApiService(ApiBaiDu mApiBaiDu, ApiGoogle mApiGoogle, ApiJinShan mApiJinShan, ApiYouDao mApiYouDao) {
        this.mApiBaiDu = mApiBaiDu;
        this.mApiGoogle = mApiGoogle;
        this.mApiJinShan = mApiJinShan;
        this.mApiYouDao = mApiYouDao;
    }

    public Observable<AbResult> translate(ETranslateFrom way, String query) {
        Observable<AbResult> resultObservable = null;
        query = query.toLowerCase();
        switch (way) {
            case BAI_DU:
                String salt = TranslateSignUtil.getRandomInt(10);
                String sign = TranslateSignUtil.getSign(BuildConfig.BAIDU_APP_ID, query, salt, BuildConfig.BAIDU_SCREAT_KEY);
                resultObservable = mApiBaiDu.translateBaiDu(query, BuildConfig.LANGUAGE_AUTO, BuildConfig.LANGUAGE_AUTO,
                        BuildConfig.BAIDU_APP_ID, salt, sign)
                        .flatMap(new Func1<BaiDuResult, Observable<AbResult>>() {
                            @Override
                            public Observable<AbResult> call(BaiDuResult baiDuResult) {
                                return Observable.just((AbResult) baiDuResult);
                            }
                        });
                break;
            case GOOGLE:
                String targetLanguage;
                String patternWords = "[\u4e00-\u9fa5 ]{1,}";//包含中文
                Pattern r = Pattern.compile(patternWords);
                Matcher m = r.matcher(query);
                if(!m.matches()){
                    targetLanguage = BuildConfig.GOOGLE_LANGUAGE_CHINEASE;
                }
                else {
                    targetLanguage = BuildConfig.GOOGLE_LANGUAGE_ENGLISH;
                }
                resultObservable = mApiGoogle.translateGoogle(query, targetLanguage)
                        .flatMap(new Func1<ResponseBody, Observable<AbResult>>() {
                            @Override
                            public Observable<AbResult> call(ResponseBody result) {
                                GoogleResult googleResult = new GoogleResult();
                                try {
                                    googleResult.setTranslationResult(result.string());
                                } catch (IOException e){
                                    TenderLog.e(e.getMessage());
                                }
                                return Observable.just((AbResult) googleResult);
                            }
                        });
                break;
            case JIN_SHAN:
                resultObservable = mApiJinShan.translateJinShan(query,
                        BuildConfig.ICIBA_KEY,
                        BuildConfig.RESULT_JSON)
                        .flatMap(new Func1<JinShanResult, Observable<AbResult>>() {
                            @Override
                            public Observable<AbResult> call(JinShanResult jinShanResult) {
                                return Observable.just((AbResult) jinShanResult);
                            }
                        });
                break;
            case YOU_DAO:
                String youDaoSalt = TranslateSignUtil.getRandomInt(10);
                String youDaoSign = TranslateSignUtil.md5YouDao(
                        new StringBuilder(BuildConfig.YOUDAO_APPKEY).append(query).append(youDaoSalt).append(BuildConfig.YOUDAO_SECKEY).toString());
                resultObservable = mApiYouDao.translateYouDao(query, BuildConfig.LANGUAGE_AUTO, BuildConfig.LANGUAGE_AUTO,
                        BuildConfig.YOUDAO_APPKEY,youDaoSalt, youDaoSign)
                        .flatMap(new Func1<YouDaoResult, Observable<AbResult>>() {
                            @Override
                            public Observable<AbResult> call(YouDaoResult youDaoResult) {
                                return Observable.just((AbResult)youDaoResult);
                            }
                        });
                break;
        }
        return resultObservable;
    }

}
