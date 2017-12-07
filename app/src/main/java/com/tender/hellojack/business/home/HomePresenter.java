package com.tender.hellojack.business.home;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu on 2017/12/7.
 */

public class HomePresenter implements HomeContract.Presenter {

    private final ResourceRepository mRepository;
    private final HomeContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;
    private boolean hasInit = false;

    public HomePresenter(ResourceRepository mRepository, HomeContract.View mView, BaseSchedule mSchedule) {
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
