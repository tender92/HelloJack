package com.tender.hellojack.business.myinfo;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.contact.UserInfo;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu on 2017/12/7.
 */

public class MyInfoPresenter implements MyInfoContract.Presenter {

    private final ResourceRepository mRepository;
    private final MyInfoContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;
    private boolean hasInit = false;

    public MyInfoPresenter(ResourceRepository mRepository, MyInfoContract.View mView, BaseSchedule mSchedule) {
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
        final UserInfo mine = UserRepository.getInstance().getUser(account).get(0);
        mine.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                mView.showMineInfo(mine);
            }
        });
        mView.showMineInfo(mine);
    }

    @Override
    public int getMineGender(String account) {
        return UserRepository.getInstance().getUser(account).get(0).getGender();
    }

    @Override
    public void updateMineAvatar(String account, String avatar) {
        UserRepository.getInstance().updateUserAvatar(account, avatar);
    }

    @Override
    public void updateMineAddress(String account, String address) {
        UserRepository.getInstance().updateUserAddress(account, address);
    }

    @Override
    public void updateMineGender(String account, int gender) {
        UserRepository.getInstance().updateUserGender(account, gender);
    }

    @Override
    public void updateMineRegion(String account, String region) {
        UserRepository.getInstance().updateUserRegion(account, region);
    }
}
