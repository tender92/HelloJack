package com.tender.hellojack.model;

import com.orhanobut.logger.Logger;

import java.io.Serializable;

/**
 * Created by boyu on 2017/10/19.
 */

public class ImageBean implements Serializable {

    public String name;       //图片的名字
    public String path;       //图片的路径
    public long size;         //图片的大小
    public int width;         //图片的宽度
    public int height;        //图片的高度
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间

    @Override
    public boolean equals(Object obj) {
        try {
            ImageBean bean = (ImageBean) obj;
            return this.path.equalsIgnoreCase(bean.path) && this.addTime == bean.addTime;
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return super.equals(obj);
    }
}
