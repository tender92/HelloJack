package com.tender.hellojack.manager;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tender.hellojack.BuildConfig;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.hellojack.utils.imageloder.UILImageLoader;
import com.tender.lbs.LocationManager;
import com.tender.speech.FilePath;
import com.tender.tools.TenderLog;
import com.tender.umengshare.DataAnalyticsManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by boyu on 2017/10/19.
 */

public class MyApplication extends Application {

    private IManager[] managers;

    public static Thread mMainThread;//主线程
    public static long mMainThreadId;//主线程id
    public static Looper mMainLooper;//循环队列
    public static Handler mHandler;//主线程Handler

    public LocationManager locationSevice;

    @Override
    public void onCreate() {
        super.onCreate();

        mMainThread = Thread.currentThread();
        mMainThreadId = mMainThread.getId();
        mMainLooper = getMainLooper();
        mHandler = new Handler();

        //日志框架初始化
        TenderLog.initLogConfig("hellojack", BuildConfig.DEBUG);

        //百度定位
        locationSevice = LocationManager.getInstance(getApplicationContext());

        initManager();
        initUmengShare();
        initImagePicker();
        initImageLoader(getApplicationContext());
        initBaiduTTS();
    }

    private void initBaiduTTS() {

    }

    private void initUmengShare() {
        //友盟统计功能初始化
        DataAnalyticsManager.getInstance().init(true);
        DataAnalyticsManager.getInstance().setDebugMode(true);
        DataAnalyticsManager.getInstance().setScenarioType(
                getApplicationContext(),
                MobclickAgent.EScenarioType.E_UM_NORMAL);

        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        UMShareAPI.get(this);

        //增加各平台key配置
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //...
    }

    /**
     * 初始化各Manager
     */
    private void initManager() {
        managers = new IManager[]{
                PrefManager.getInstance()
        };

        for (IManager manager : managers) {
            manager.init(getApplicationContext());
        }

        //用户账号统计
        DataAnalyticsManager.getInstance().onProfileSignIn("com.tender.hellojack", PrefManager.getUserAccount());
    }

    /**
     * 初始化仿微信控件ImagePicker
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new UILImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    /**
     * ImageLoader 图片组件初始化
     * @param context
     */
    private void initImageLoader(Context context) {

//        File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils.getOwnCacheDirectory(context, "CSDN_LQR/cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
//                .discCacheFileCount(10) //缓存的文件数量
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
//                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(ImageLoaderUtil.options)//DisplayImageOptions.createSimple()
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
    }

}
