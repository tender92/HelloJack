package com.tender.hellojack.business.myinfo;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.contact.UserInfo;

/**
 * Created by boyu on 2017/12/7.
 */

public class MyInfoContract {

    interface View extends IView<Presenter> {
        void showGenderDialog();
        void showMineInfo(UserInfo mineInfo);
    }
    interface Presenter extends IPresenter {
        void getMineInfo(String account);
        int getMineGender(String account);
        void updateMineAvatar(String account, String avatar);
        void updateMineAddress(String account, String address);
        void updateMineGender(String account, int gender);
        void updateMineRegion(String account, String region);
    }
}
