package com.tender.hellojack.business.mine;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.contact.UserInfo;

/**
 * Created by boyu
 */
public class MineContract {
    interface View extends IView<Presenter> {
        void showMyQRCode(UserInfo userInfo);
        void showMineInfo(UserInfo userInfo);
    }

    interface Presenter extends IPresenter {
        void createMine();
        void getMineInfo();
        void getMineInfo2();
        String getMineAccount();
    }
}
