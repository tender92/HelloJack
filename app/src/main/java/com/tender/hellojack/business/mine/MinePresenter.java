package com.tender.hellojack.business.mine;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.contact.UserInfo;
import com.tender.tools.manager.PrefManager;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class MinePresenter implements MineContract.Presenter {

    private final ResourceRepository mRepository;
    private final MineContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    private String mineAccount;

    public MinePresenter(ResourceRepository mRepository, MineContract.View mView, BaseSchedule mSchedule) {
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
    public void createMine() {
        mineAccount = PrefManager.getUserAccount();
        UserInfo mine = new UserInfo();
        mine.setAccount(mineAccount);
        UserRepository.getInstance().addNewUser(mine, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, null);
    }

    @Override
    public void getMineInfo() {
        final UserInfo mine = UserRepository.getInstance().getUser(mineAccount).get(0);
        mine.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                mView.showMineInfo(mine);
            }
        });
        mView.showMineInfo(mine);
    }

    @Override
    public void getMineInfo2() {
        final UserInfo mine = UserRepository.getInstance().getUser(mineAccount).get(0);
        mine.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                mView.showMyQRCode(mine);
            }
        });
        mView.showMyQRCode(mine);
    }

    @Override
    public String getMineAccount() {
        return mineAccount;
    }
}