package com.tender.router;

import android.content.Context;

/**
 * Created by boyu on 2017/11/3.
 */

public class RouterHelper {

    /**
     * 在需要被调用的Activity或Service所在的Application中添加映射
     * 示例：RouterHelper.putRouter(ActivityRule.ACTIVITY_SCHEME + "user.login", LoginActivity.class);
     * @param pattern
     * @param klass
     * @param <T>
     */
    public static <T> void putRouter(String pattern, Class<T> klass) {
        RouterHelperInternal.getInstance().putRouter(pattern, klass);
    }

    /**
     * 在调用Activity或Service的地方调用
     * 示例：Intent i = RouterHelper.invoke(getActivity(), ActivityRule.ACTIVITY_SCHEME + "user.login");
     * @param ctx context
     * @param pattern Activity或Service的路由url
     * @param <V> Activity或Service
     * @return V
     */
    public static <V> V invoke(Context ctx, String pattern) {
        return RouterHelperInternal.getInstance().invoke(ctx, pattern);
    }

}
