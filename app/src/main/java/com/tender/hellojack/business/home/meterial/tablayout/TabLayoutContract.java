package com.tender.hellojack.business.home.meterial.tablayout;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class TabLayoutContract {
    interface View extends IView<Presenter> {
        void showBottomSheetDialog();
        void hideBottomSheetDialog();
    }

    interface Presenter extends IPresenter {

    }
}
