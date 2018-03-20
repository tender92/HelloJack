package com.tender.hellojack.service.listenclipboard;

/**
 * Created by boyu on 2018/1/31.
 */

public class ListenClipBoardContract {

    interface View<T extends Presenter> {
        void setPresenter(T presenter);
        void initData();
    }

    interface Presenter {
        void start();
        void addPrimaryClipChangedListener();
    }
}
