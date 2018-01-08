package com.tender.hellojack.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.lqr.emoji.LQRUIKit;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tender.hellojack.BuildConfig;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.hellojack.utils.imageloder.UILImageLoader;
import com.tender.lbs.baidu.BDLocationImpl;
import com.tender.tools.TenderLog;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.string.UUIDGenerator;
import com.tender.umengshare.DataAnalyticsManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by boyu on 2017/10/19.
 */

public class MyApplication extends NimApplication {

    private IManager[] managers;
    private static Set<Activity> activities = new HashSet<>();
    private ModuleManager moduleManager;

    public BDLocationImpl locationService;

    @Override
    public void onCreate() {
        super.onCreate();

        moduleManager = new ModuleManager(this);
        moduleManager.onInit();

        initRealm();

        initNim();

        //emoji aar初始化
        LQRUIKit.init(getApplicationContext());

        //日志框架初始化
        TenderLog.initLogConfig("hellojack", BuildConfig.DEBUG);

        //百度定位
        locationService = BDLocationImpl.getInstance(getApplicationContext());

        if (!StringUtil.hasValue(PrefManager.getUserAccount())) {
            String uuid = UUIDGenerator.generate();
            final String account = "user" + uuid.substring(uuid.length() - 6);
            PrefManager.setUserAccount(account);
        }

        //用户账号统计
        DataAnalyticsManager.getInstance().onProfileSignIn("com.tender.hellojack", PrefManager.getUserAccount());


        initUmengShare();
        initImagePicker();
        initImageLoader(getApplicationContext());
        initBaiduTTS();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        moduleManager.onTerminate();
    }

    void initRealm() {
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("jack.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
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

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void exit(int status) {
        for (Activity activity : activities) {
            activity.finish();
        }

        if (status != 99) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(status);
        }
    }

}
