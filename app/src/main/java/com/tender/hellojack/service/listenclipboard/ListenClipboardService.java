package com.tender.hellojack.service.listenclipboard;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tender.hellojack.business.translate.service.TranslateApiProvider;
import com.tender.hellojack.business.translate.service.WrapApiService;
import com.tender.hellojack.utils.Injection;

/**
 * Created by boyu on 2018/1/31.
 */

public class ListenClipboardService extends Service implements ListenClipBoardContract.View {

    private ListenClipBoardContract.Presenter mPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        mPresenter = new ListenClipboardPresenter(Injection.provideLiteOrm(),
                Injection.provideWrapApiService(), this,
                Injection.provideSchedule());
        mPresenter.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void setPresenter(ListenClipBoardContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initData() {
        mPresenter.addPrimaryClipChangedListener();
    }

    public static void start(Context context) {
        Intent serviceIntent = new Intent(context, ListenClipboardService.class);
        context.startService(serviceIntent);
    }
}
