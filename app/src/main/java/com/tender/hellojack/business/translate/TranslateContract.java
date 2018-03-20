package com.tender.hellojack.business.translate;

import android.content.Context;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.translate.ETranslateFrom;
import com.tender.hellojack.model.translate.Result;

import java.util.List;

/**
 * Created by boyu
 */
public class TranslateContract {
    interface View extends IView<Presenter> {
        String getInput();
        void onPrepareTranslate();
        void onTranslateComplete();
        void onTranslateError(Throwable e);
        void addTagForView(Result result);
        void showSoundView(boolean isShow);
        void showFavoriteView(boolean isFavorite);
        void addExplainItem(String explain);
        void initTranslateWay(ETranslateFrom from);
        void attachLocalDic(List<String> dic);
        void startFavoriteAnim(android.view.View view);
    }

    interface Presenter extends IPresenter {
        void executeSearch();
        void checkTranslateWay();
        void checkVersion();
        void analysisLocalDic(Context context);
        void favoriteWord(Result result);
        void unFavoriteWord(Result result);
        void playSound(Context context, String fileName, String mp3Url);
    }
}
