package com.tender.hellojack.business.myinfo.changename;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.UserInfo;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class ChangeNamePresenter implements ChangeNameContract.Presenter {

    private final ResourceRepository mRepository;
    private final ChangeNameContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    public ChangeNamePresenter(ResourceRepository mRepository, ChangeNameContract.View mView, BaseSchedule mSchedule) {
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
    public void getMineInfo(String account) {
        final UserInfo userInfo = UserRepository.getInstance().getUser(account).get(0);
        userInfo.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                mView.showMineName(userInfo.getName());
            }
        });
        mView.showMineName(userInfo.getName());
    }

    @Override
    public void updateUserName(String account, String name) {
        UserRepository.getInstance().updateUserName(account, name);
    }
}