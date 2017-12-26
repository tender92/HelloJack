package com.tender.hellojack.business.webview;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class WebViewContract {
    interface View extends IView<Presenter> {

    }

    interface Presenter extends IPresenter {

    }
}
