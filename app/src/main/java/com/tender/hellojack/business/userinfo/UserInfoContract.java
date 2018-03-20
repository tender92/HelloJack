package com.tender.hellojack.business.userinfo;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.contact.UserInfo;

/**
 * Created by boyu
 */
public class UserInfoContract {
    interface View extends IView<Presenter> {
        void showUserInfo(UserInfo userInfo);
    }

    interface Presenter extends IPresenter {
        void getUserInfo(String account);
    }
}
