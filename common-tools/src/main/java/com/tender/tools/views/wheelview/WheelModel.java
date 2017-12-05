package com.tender.tools.views.wheelview;

import android.graphics.Bitmap;

/**
 * Created by zhangmingsong on 2017/11/22.
 */

public class WheelModel {
    public WheelModel(String key,Bitmap bitmap, String text){
        this.key = key;
        this.bitmap = bitmap;
        this.value = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
}
