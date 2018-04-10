package com.tender.hellojack.business.home.meterial.swipelistview;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

import java.util.List;

/**
 * Created by boyu
 */
public class SwipeListContract {
    interface View extends IView<Presenter> {

    }

    interface Presenter extends IPresenter {
        List<CardTransBean> getTransList();
        void onClickLeft(CardTransBean transBean);
        void onClickRight(CardTransBean transBean);
        void onClickItem(CardTransBean transBean);
    }
}
