package com.tender.router;

import android.content.Context;

import com.tender.router.base.ActivityRule;
import com.tender.router.base.IRule;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by boyu on 2017/11/3.
 */

public class RouterHelperInternal {

    private static RouterHelperInternal instance;

    /** scheme->路由规则 */
    private HashMap<String, IRule> mRules;

    private RouterHelperInternal() {
        mRules = new HashMap<>();
        initDefaultRouter();
    }

    private void initDefaultRouter() {
        addRule(ActivityRule.ACTIVITY_SCHEME, new ActivityRule());
    }

    synchronized public static RouterHelperInternal getInstance(){
        if (instance == null) {
            instance = new RouterHelperInternal();
        }
        return instance;
    }

    public void addRule(String scheme, IRule rule) {
        mRules.put(scheme, rule);
    }

    public <T, V> IRule<T, V> getRule(String pattern) {
        Set<String> keySet = mRules.keySet();
        IRule<T, V> rule = null;
        for (String scheme : keySet) {
            if (pattern.startsWith(scheme)) {
                rule = mRules.get(scheme);
                break;
            }
        }
        return rule;
    }

    public <T> void putRouter(String pattern, Class<T> tClass) {
        IRule<T, ?> rule = getRule(pattern);
        if (rule == null) {
            //异常处理
        }
        rule.putRule(pattern, tClass);
    }

    public <V> V invoke(Context context, String pattern) {
        IRule<?, V> rule = getRule(pattern);

        return rule.invoke(context, pattern);
    }
}
