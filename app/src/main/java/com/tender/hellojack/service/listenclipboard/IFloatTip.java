package com.tender.hellojack.service.listenclipboard;

import com.tender.hellojack.model.translate.Result;

/**
 * Created by boyu on 2018/1/31.
 */

public interface IFloatTip {
    void onComplete();

    void errorPoint(String error);

    void showResult(Result result, boolean isShowFavorite);

    void initWithFavorite(Result result);

    void initWithNotFavorite(Result result);
}
