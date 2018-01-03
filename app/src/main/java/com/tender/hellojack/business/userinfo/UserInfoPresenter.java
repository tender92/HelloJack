package com.tender.hellojack.business.userinfo;

import android.content.Intent;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.UserInfo;
import com.tender.tools.IntentConst;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
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
    public void getUserInfo(String account) {
        final UserInfo userInfo = UserRepository.getInstance().getUser(account).get(0);
        userInfo.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                mView.showUserInfo(userInfo);
            }
        });
        mView.showUserInfo(userInfo);
    }
}