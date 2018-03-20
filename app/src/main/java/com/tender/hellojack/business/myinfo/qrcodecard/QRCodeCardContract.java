package com.tender.hellojack.business.myinfo.qrcodecard;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.contact.UserInfo;

/**
 * Created by boyu on 2017/12/8.
 */

public class QRCodeCardContract {

    interface View extends IView<Presenter> {
        void showMineInfo(UserInfo userInfo);
    }

    interface Presenter extends IPresenter {
        void getMineInfo(String account);
    }
}
