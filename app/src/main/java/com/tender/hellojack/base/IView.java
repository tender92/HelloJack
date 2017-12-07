package com.tender.hellojack.base;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.FragmentEvent;

/**
 * Created by boyu on 2017/12/7.
 */

public interface IView<T> {
    void setPresenter(T presenter);

    void initUIData();

    /**
     * 需要取消订阅的生命周期时机
     *
     * @param event
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindUntilEvent(FragmentEvent event);
}
