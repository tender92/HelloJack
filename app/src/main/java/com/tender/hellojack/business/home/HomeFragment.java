package com.tender.hellojack.business.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.dagger2.activity.DFirstActivity;
import com.tender.hellojack.business.home.meterial.CoordinatorLayoutActivity;
import com.tender.hellojack.business.home.once.OnceActivity;
import com.tender.hellojack.manager.MyApplication;
import com.tender.tools.utils.ui.DialogUtil;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.lbs.baidu.BDLocationImpl;
import com.tender.lbs.baidu.HJLocationListener;
import com.tender.tools.views.dialog.SelectDateDialog;
import com.tender.tools.views.dialog.WheelDialogCallBack;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by boyu on 2017/12/7.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeContract.Presenter mPresenter;

    private Button btnOnce, btnMaterial;
    private Button btnShare, btnDagger2;
    private TextView tvLocationInfo;

    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    public String url ="https://mobile.umeng.com/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_home, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        btnOnce = (Button) root.findViewById(R.id.btn_home_once);
        btnMaterial = (Button) root.findViewById(R.id.btn_home_design);
        btnShare = (Button) root.findViewById(R.id.btn_home_share);
        btnDagger2 = (Button) root.findViewById(R.id.btn_home_dagger2);
        tvLocationInfo = (TextView) root.findViewById(R.id.tv_home_location);

        RxView.clicks(btnOnce).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(mActivity, OnceActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(btnMaterial).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(mActivity, CoordinatorLayoutActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(btnShare).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showShareDialog();
                    }
                });
        RxView.clicks(btnDagger2).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(new Intent(mActivity, DFirstActivity.class));
                    }
                });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initUIData() {
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        BDLocationImpl locationManager = ((MyApplication)mActivity.getApplication()).locationService;
        locationManager.registerListener(new HJLocationListener(new HJLocationListener.CallBack() {
            @Override
            public void returnLocation(BDLocation location) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                printLocationInfo(sb.toString());
            }
        }));
        locationManager.setLocationOption(locationManager.getDefaultLocationClientOption());
        locationManager.start();// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
    }

    @Override
    public void showNetLoading(String tip) {
        super.showWaitingDialog(tip);
    }

    @Override
    public void hideNetLoading() {
        super.hideWaitingDialog();
    }

    private void printLocationInfo(String s) {
        final String locationInfo = s;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvLocationInfo.setText(locationInfo);
            }
        });
    }

    private void showShareDialog() {
        mShareListener = new CustomShareListener(mActivity);
        mShareAction = new ShareAction(mActivity)
                .setDisplayList(
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                        SHARE_MEDIA.SINA,SHARE_MEDIA.ALIPAY)
                .addButton("umeng_sharebutton_copy", "umeng_sharebutton_copy", "umeng_socialize_copy", "umeng_socialize_copy")
                .addButton("umeng_sharebutton_copyurl", "umeng_sharebutton_copyurl", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mShowWord.equals("umeng_sharebutton_copy")) {
                            DialogUtil.showHint(mActivity, "复制文本按钮");
                        } else if (snsPlatform.mShowWord.equals("umeng_sharebutton_copyurl")) {
                            DialogUtil.showHint(mActivity, "复制链接按钮");
                        }else if(share_media == SHARE_MEDIA.SMS) {
                            new ShareAction(mActivity).withText("来自分享面板标题")
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        }else {
                            UMWeb web = new UMWeb(url);
                            web.setTitle("来自分享面板标题");
                            web.setDescription("来自分享面板内容");
                            web.setThumb(new UMImage(mActivity, R.mipmap.hj_mine_default_header));
                            new ShareAction(mActivity).withMedia(web)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        }
                    }
                });
        mShareAction.open();
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
//            mToolbar.setNavigationIcon(R.mipmap.hj_toolbar_back);
            mActivity.setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });

            ImageView ivRight = (ImageView) mToolbar.findViewById(R.id.iv_actionbar_right);
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SelectDateDialog(mActivity, "", new WheelDialogCallBack() {
                        @Override
                        public void onCallback(Context context, String selectString) {
                            DialogUtil.showHint(mActivity, selectString);
                        }
                    }).show();
                }
            });

            mTitle.setText("首页");
        }
    }

    private class CustomShareListener implements UMShareListener {

        private Context context;

        private CustomShareListener(Activity activity) {
            this.context = activity;
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                DialogUtil.showHint(context, platform + " 收藏成功啦");
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    DialogUtil.showHint(context, platform + " 分享成功啦");
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                DialogUtil.showHint(context, platform + " 分享失败啦");
                if (t != null) {
                    Logger.e(t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            DialogUtil.showHint(context, platform + " 分享取消了");
        }
    }
}
