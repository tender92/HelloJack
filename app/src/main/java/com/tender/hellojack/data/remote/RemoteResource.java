package com.tender.hellojack.data.remote;

import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tender.hellojack.data.IResource;
import com.tender.hellojack.model.UserInfo;
import com.tender.instant.message.contact.ContactHttpClient;
import com.tender.instant.message.nimsdk.NimAccountSDK;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by boyu on 2017/12/7.
 */

public class RemoteResource implements IResource {
    private static RemoteResource instance;

    public static RemoteResource getInstance() {
        if (instance == null) {
            instance = new RemoteResource();
        }
        return instance;
    }

    @Override
    public Observable<Object> login(final String userName, final String token) {
        return Observable.create(new Observable.OnSubscribe<Object>() {

            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                NimAccountSDK.login(userName, token, new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        subscriber.onNext(param);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailed(int code) {
                        subscriber.onError(new Throwable(String.valueOf(code)));
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onException(Throwable exception) {
                        subscriber.onError(exception);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    @Override
    public Observable<Object> register(final String account, final String name, final String pwd) {
        return Observable.create(new Observable.OnSubscribe<Object>() {

            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                ContactHttpClient.getInstance().register(account, name, pwd, new ContactHttpClient.ContactHttpCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        subscriber.onNext(aVoid);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailed(int code, String errorMsg) {
                        subscriber.onError(new Throwable(errorMsg + "[" + code + "]"));
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }
}
