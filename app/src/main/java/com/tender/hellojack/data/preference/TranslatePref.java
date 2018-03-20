package com.tender.hellojack.data.preference;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tender.hellojack.model.translate.ETranslateFrom;
import com.tender.tools.utils.ui.UIUtil;

/**
 * Created by boyu on 2018/2/2.
 */

public class TranslatePref {

    //翻译相关
    private static final String TRANSLATE_FROM = "translate_from";

    private static volatile TranslatePref instance;
    private SharedPreferences pre;

    private TranslatePref() {
        pre = PreferenceManager.getDefaultSharedPreferences(UIUtil.getAppContext());
    }

    public static TranslatePref getInstance() {
        if (instance == null) {
            synchronized (TranslatePref.class) {
                if (instance == null) {
                    instance = new TranslatePref();
                }
            }
        }
        return instance;
    }

    public void saveTranslateWay(String way) {
        instance.pre.edit().putString(TRANSLATE_FROM, way).commit();
    }
    public ETranslateFrom getTranslateWay() {
        return ETranslateFrom.valueOf(instance.pre.getString(TRANSLATE_FROM, ETranslateFrom.BAI_DU.name()));
    }
}
