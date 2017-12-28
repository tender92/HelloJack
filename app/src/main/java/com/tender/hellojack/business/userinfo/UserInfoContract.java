package com.tender.hellojack.business.userinfo;

import android.content.Intent;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.UserInfo;

/**
 * Created by boyu
 */
public class UserInfoContract {
    interface View extends IView<Presenter> {
        void showUserInfo(UserInfo userInfo);
    }

    interface Presenter extends IPresenter {
        void handleIntentParams(Intent intent);
    }
}
