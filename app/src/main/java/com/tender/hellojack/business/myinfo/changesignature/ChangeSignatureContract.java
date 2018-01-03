package com.tender.hellojack.business.myinfo.changesignature;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class ChangeSignatureContract {
    interface View extends IView<Presenter> {
        void showMineSignature(String signature);
    }

    interface Presenter extends IPresenter {
        void getMineInfo(String account);
        void updateUserSignature(String account, String signature);
    }
}
