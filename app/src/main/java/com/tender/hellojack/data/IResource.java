package com.tender.hellojack.data;

import rx.Observable;

/**
 * Created by boyu on 2017/12/7.
 */

public interface IResource {
    Observable<Object> login(String userName, String token);

    Observable<Object> register(String account, String name, String pwd);
}
