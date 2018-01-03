package com.tender.hellojack.business.myinfo.qrcodecard;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.UserInfo;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
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

    @Override
    public void getMineInfo(String account) {
        final UserInfo mine = UserRepository.getInstance().getUser(account).get(0);
        mine.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                mView.showMineInfo(mine);
            }
        });
        mView.showMineInfo(mine);
    }
}
