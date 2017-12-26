package com.tender.hellojack.business.login;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class LoginContract {
    interface View extends IView<Presenter> {
        void showRegionDialog();
        void showOrHidePwd();
        String getAccount();
        String getUserPwd();
        void goToHome();
    }

    interface Presenter extends IPresenter {
        void login();
    }
}
