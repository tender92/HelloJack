package com.tender.hellojack.business.register;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class RegisterContract {
    interface View extends IView<Presenter> {
        void showOrHidePwd();
        String getUserAccount();
        String getUserName();
        String getUserPwd();
        void goToLogin(String account, String pwd);
    }

    interface Presenter extends IPresenter {
        void register();
    }
}
