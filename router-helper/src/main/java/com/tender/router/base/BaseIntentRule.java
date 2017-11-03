package com.tender.router.base;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * Created by boyu on 2017/11/3.
 */

public class BaseIntentRule<T> implements IRule<T, Intent> {

    private HashMap<String, Class<T>> mRules;

    public BaseIntentRule() {
        mRules = new HashMap<>();
    }

    @Override
    public void putRule(String key, Class<T> tClass) {
        mRules.put(key, tClass);
    }

    @Override
    public Intent invoke(Context context, String key) {
        Class<T> tClass = mRules.get(key);
        if (tClass == null) {
            //异常处理
        }
        return new Intent(context, tClass);
    }
}
