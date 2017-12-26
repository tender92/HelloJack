package com.tender.hellojack.business.mine.scan;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class ScanContract {
    interface View extends IView<Presenter> {
        void selectBottomOne(int selected);
        void showPopUpMenu();
        void vibrate();
    }

    interface Presenter extends IPresenter {

    }
}
