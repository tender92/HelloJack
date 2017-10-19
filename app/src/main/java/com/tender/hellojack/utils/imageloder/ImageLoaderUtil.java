package com.tender.hellojack.utils.imageloder;

import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tender.hellojack.R;

/**
 * Created by boyu on 2017/10/19.
 */

public class ImageLoaderUtil {

    public static DisplayImageOptions options = new DisplayImageOptions.Builder()//
            .showImageOnLoading(R.mipmap.default_image)         //设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.default_image)       //设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.default_image)            //设置图片加载/解码过程中错误时候显示的图片
            .cacheInMemory(true)                                //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                                  //设置下载的图片是否缓存在SD卡中
            .build();                                           //构建完成

    public static void loadNetImage(String imgUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
    }

    public static void loadLocalImage(String path, ImageView imageView) {
        ImageLoader.getInstance().displayImage(Uri.parse("file://" + path).toString(), imageView, options);
    }
}
