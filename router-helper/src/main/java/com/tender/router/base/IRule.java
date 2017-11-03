package com.tender.router.base;

import android.content.Context;

/**
 * Created by boyu on 2017/11/3.
 */

public interface IRule<T, V> {

    void putRule(String key, Class<T> tClass);

    V invoke(Context context, String key);

}
