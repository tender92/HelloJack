package com.tender.hellojack.business.login;

import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.instant.message.model.UserCache;
import com.tender.instant.message.nimsdk.NimAccountSDK;
import com.tender.instant.message.nimsdk.NimUserInfoSDK;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.UIUtil;
import com.trello.rxlifecycle.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final ResourceRepository mRepository;
    private final LoginContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    public LoginPresenter(ResourceRepository mRepository, LoginContract.View mView, BaseSchedule mSchedule) {
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
    public void login() {
        mSubscription.add(Observable.just(checkFormIllegal())
                .flatMap(new Func1<Boolean, Observable<Object>>() {
                    @Override
                    public Observable<Object> call(Boolean aBoolean) {
                        return mRepository.login(mView.getAccount(), mView.getUserPwd());
                    }
                })
                .subscribeOn(mSchedule.io())
                .observeOn(mSchedule.ui())
                .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideNetLoading();
                        String message = e.getMessage();
                        if ("302".equals(message) || "404".equals(message)) {
                            UIUtil.showToast("账号或密码错误，请重新填写");
                        } else {
                            UIUtil.showToast(message);
                        }
                    }

                    @Override
                    public void onNext(Object o) {
                        mView.hideNetLoading();
                        LoginInfo loginInfo = (LoginInfo) o;
                        //保存用户名到内存中
                        UserCache.setAccount(mView.getAccount());
                        //保存用户信息到本地，方便下次启动APP做自动登录用
                        NimAccountSDK.saveUserAccount(mView.getAccount());
                        NimAccountSDK.saveUserToken(mView.getUserPwd());

                        //更新本地用户资料
                        List<String> list = new ArrayList<String>();
                        list.add(UserCache.getAccount());
                        NimUserInfoSDK.getUserInfoFormServer(list, null);

                        mView.goToHome();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showNetLoading("正在登录...");
                    }
                }));
    }

    private boolean checkFormIllegal() {
        String username = mView.getAccount();
        String userPwd = mView.getUserPwd();
        if (StringUtil.isEmpty(username)) {
            UIUtil.showToast("用户名不能为空");
            return false;
        }
        if (StringUtil.isEmpty(userPwd)) {
            UIUtil.showToast("密码不能为空");
            return false;
        }
        return true;
    }
}