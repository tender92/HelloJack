package com.tender.hellojack.business.userinfo;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;

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
}