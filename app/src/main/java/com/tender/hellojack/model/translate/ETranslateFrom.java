package com.tender.hellojack.model.translate;

import com.tender.hellojack.business.translate.service.ApiBaiDu;
import com.tender.hellojack.business.translate.service.ApiGoogle;
import com.tender.hellojack.business.translate.service.ApiJinShan;
import com.tender.hellojack.business.translate.service.ApiYouDao;

/**
 * Created by boyu on 2018/1/31.
 */

public enum ETranslateFrom {
    BAI_DU(0, "百度", "http://api.fanyi.baidu.com/", ApiBaiDu.class),
    YOU_DAO(1,"有道","http://openapi.youdao.com/",ApiYouDao.class),
    JIN_SHAN(2,"金山","http://dict-co.iciba.com/",ApiJinShan.class),
    GOOGLE(3, "谷歌", "http://translate.google.cn/", ApiGoogle.class);

    private int index;
    private String name;
    private String url;
    private Class aqiClass;

    ETranslateFrom(int index, String name, String url, Class aqiClass) {
        this.index = index;
        this.name = name;
        this.url = url;
        this.aqiClass = aqiClass;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Class getAqiClass() {
        return aqiClass;
    }
}
