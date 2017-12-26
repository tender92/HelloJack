package com.tender.hellojack.business.register;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.tools.utils.NetworkUtil;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.UIUtil;
import com.trello.rxlifecycle.android.FragmentEvent;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private final ResourceRepository mRepository;
    private final RegisterContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    public RegisterPresenter(ResourceRepository mRepository, RegisterContract.View mView, BaseSchedule mSchedule) {
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
    public void register() {
        mSubscription.add(Observable.just("")
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return checkFormIllegal();
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return NetworkUtil.isNetAvailable(UIUtil.getAppContext());
                    }
                })
                .flatMap(new Func1<String, Observable<Object>>() {
                    @Override
                    public Observable<Object> call(String s) {
                        return mRepository.register(mView.getUserAccount(), mView.getUserName(), mView.getUserPwd());
                    }
                })
                .observeOn(mSchedule.ui())
                .compose(mView.<Object>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideNetLoading();
                        UIUtil.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        mView.hideNetLoading();
                        UIUtil.showToast("注册成功");
                        mView.goToLogin(mView.getUserAccount(), mView.getUserPwd());
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showNetLoading("正在注册...");
                    }
                }));
    }

    private boolean checkFormIllegal() {
        String account = mView.getUserAccount().trim();
        String name = mView.getUserName().trim();
        String password = mView.getUserPwd().trim();
        if (!StringUtil.hasValue(account) || account.length() > 20) {
            UIUtil.showToast("帐号限20位字母或者数字");
            return false;
        }
        if (!StringUtil.hasValue(name) || name.length() > 20) {
            UIUtil.showToast("昵称限10位汉字、字母或者数字");
            return false;
        }
        if (!StringUtil.hasValue(password) || password.length() < 6 || password.length() > 20) {
            UIUtil.showToast("密码必须为6~20位字母或者数字");
            return false;
        }

        return true;
    }
}