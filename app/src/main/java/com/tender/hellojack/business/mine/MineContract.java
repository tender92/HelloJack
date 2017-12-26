package com.tender.hellojack.business.mine;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class MineContract {
    interface View extends IView<Presenter> {
        void showMyQRCode();
    }

    interface Presenter extends IPresenter {

    }
}
