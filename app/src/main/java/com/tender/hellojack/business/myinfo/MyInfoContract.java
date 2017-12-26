package com.tender.hellojack.business.myinfo;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu on 2017/12/7.
 */

public class MyInfoContract {

    interface View extends IView<Presenter> {
        void showGenderDialog();
    }
    interface Presenter extends IPresenter {}
}
