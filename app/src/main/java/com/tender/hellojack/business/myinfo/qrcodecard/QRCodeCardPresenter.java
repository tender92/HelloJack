package com.tender.hellojack.business.myinfo.qrcodecard;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu on 2017/12/8.
 */

public class QRCodeCardPresenter implements QRCodeCardContract.Presenter {

    private final ResourceRepository mRepository;
    private final QRCodeCardContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription  mSubscription;
    private boolean hasInit = false;

    public QRCodeCardPresenter(ResourceRepository mRepository, QRCodeCardContract.View mView, BaseSchedule mSchedule) {
        this.mRepository = mRepository;
        this.mView = mView;
        this.mSchedule = mSchedule;

        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!hasInit) {
            mView.initUIData();;
            hasInit = true;
        }
    }
}
