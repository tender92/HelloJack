package com.tender.hellojack.business.myinfo.qrcodecard;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu on 2017/12/8.
 */

public class QRCodeCardContract {

    interface View extends IView<Presenter> {}

    interface Presenter extends IPresenter {}
}
