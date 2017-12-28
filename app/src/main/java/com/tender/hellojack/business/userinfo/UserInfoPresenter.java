package com.tender.hellojack.business.userinfo;

import android.content.Intent;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.model.UserInfo;
import com.tender.tools.IntentConst;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class UserInfoPresenter implements UserInfoContract.Presenter {

    private final ResourceRepository mRepository;
    private final UserInfoContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    public UserInfoPresenter(ResourceRepository mRepository, UserInfoContract.View mView, BaseSchedule mSchedule) {
        this.mRepository = mRepository;
        this.mView = mView;
        this.mSchedule = mSchedule;

        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!hasInit) {
            mView.initUIData();
            hasInit = true;
        }
    }

    @Override
    public void handleIntentParams(Intent intent) {
        mSubscription.add(Observable.just(intent)
                .filter(new Func1<Intent, Boolean>() {
                    @Override
                    public Boolean call(Intent intent) {
                        return intent != null;
                    }
                })
                .map(new Func1<Intent, String>() {
                    @Override
                    public String call(Intent intent) {
                        return intent.getStringExtra(IntentConst.IRParam.MY_FRIENDS_ACCOUNT);
                    }
                })
                .flatMap(new Func1<String, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> call(String account) {
                        return mRepository.getUserInfo(account);
                    }
                })
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        mView.showUserInfo(userInfo);
                    }
                }));
    }
}