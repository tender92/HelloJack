package com.tender.hellojack.business.myinfo.changename;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class ChangeNameContract {
    interface View extends IView<Presenter> {
        void showMineName(String name);
    }

    interface Presenter extends IPresenter {
        void getMineInfo(String account);
        void updateUserName(String account, String name);
    }
}
