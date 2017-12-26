package com.tender.hellojack.business.mine.cardpacket;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class CardPacketPresenter implements CardPacketContract.Presenter {

    private final ResourceRepository mRepository;
    private final CardPacketContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    public CardPacketPresenter(ResourceRepository mRepository, CardPacketContract.View mView, BaseSchedule mSchedule) {
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