package com.tender.hellojack.base;

/**
 * Created by boyu on 2017/10/18.
 */

public interface IToolBar {

    void showToolbar();

    void hideToolbar();

    /**
     * resId为drawable资源文件
     * @param resId
     */
    void updateToolbarBg(int resId);

    void showLeftButton(boolean isShow);

    void showRightButton(boolean isShow);

    void updateTitle(String content);

    void clickRightButton(Runnable runnable);
}
