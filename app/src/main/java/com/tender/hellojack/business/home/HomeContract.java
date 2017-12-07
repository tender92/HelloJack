package com.tender.hellojack.business.home;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu on 2017/12/7.
 */

public class HomeContract {

    interface View extends IView<Presenter> {}

    interface Presenter extends IPresenter {}
}
