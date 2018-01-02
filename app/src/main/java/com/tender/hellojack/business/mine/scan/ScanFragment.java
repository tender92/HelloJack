package com.tender.hellojack.business.mine.scan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.lqr.imagepicker.ui.ImageGridActivity;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.manager.threadpool.ThreadPoolFactory;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.IntentConst;
import com.tender.tools.factory.PopupWindowFactory;
import com.tender.tools.utils.DisplayUtil;
import com.tender.tools.utils.UIUtil;
import com.tender.tools.utils.string.StringUtil;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import rx.functions.Action1;
import rx.functions.Func1;

public class ScanFragment extends BaseFragment implements ScanContract.View, QRCodeView.Delegate {

    private ScanContract.Presenter mPresenter;
    private ZXingView zvScan;
    private ImageView ivScanCode, ivCover, ivVista, ivTranslate;
    private LinearLayout llScan, llCover, llVista, llTranslate;

    private FrameLayout flPopUp;
    private PopupWindow pwMenu;

    @Override
    public void onStart() {
        super.onStart();
        zvScan.startCamera();
        zvScan.startSpotAndShowRect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        zvScan.startCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        zvScan.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConst.IRCode.SCAN_IMAGE_PICKER && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    rx.Observable.just(images.get(0).path)
                            .map(new Func1<String, String>() {
                                @Override
                                public String call(String s) {
                                    return QRCodeDecoder.syncDecodeQRCode(s);
                                }
                            })
                            .subscribeOn(ScheduleProvider.getInstance().io())
                            .observeOn(ScheduleProvider.getInstance().ui())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String result) {
                                    if (StringUtil.isEmpty(result)) {
                                        UIUtil.showToast("扫描失败");
                                    } else {
                                        vibrate();
                                        zvScan.startSpot();
                                        UIUtil.showToast("扫描结果：" + result);
                                        mActivity.finish();
                                    }
                                }
                            });
//                    ThreadPoolFactory.getNormalPool().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            String result = QRCodeDecoder.syncDecodeQRCode(images.get(0).path);
//                            if (StringUtil.isEmpty(result)) {
//                                UIUtil.showToast("扫描失败");
//                            } else {
//                                vibrate();
//                                zvScan.startSpot();
//                                UIUtil.showToast("扫描结果：" + result);
//                                mActivity.finish();
//                            }
//                        }
//                    });
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_scan, container, false);
        zvScan = (ZXingView) root.findViewById(R.id.zv_scan_zxing);
        ivScanCode = (ImageView) root.findViewById(R.id.iv_scan_scan_code);
        ivCover = (ImageView) root.findViewById(R.id.iv_scan_cover);
        ivVista = (ImageView) root.findViewById(R.id.iv_scan_vista);
        ivTranslate = (ImageView) root.findViewById(R.id.iv_scan_translate);
        llScan = (LinearLayout) root.findViewById(R.id.ll_scan_scan);
        llCover = (LinearLayout) root.findViewById(R.id.ll_scan_cover);
        llVista = (LinearLayout) root.findViewById(R.id.ll_scan_vista);
        llTranslate = (LinearLayout) root.findViewById(R.id.ll_scan_translate);

        RxView.clicks(llScan).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                selectBottomOne(0);
            }
        });
        RxView.clicks(llCover).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                selectBottomOne(1);
            }
        });
        RxView.clicks(llVista).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                selectBottomOne(2);
            }
        });
        RxView.clicks(llTranslate).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                selectBottomOne(3);
            }
        });

        return root;
    }

    @Override
    public void initUIData() {
        zvScan.setDelegate(this);
        selectBottomOne(0);
    }

    @Override
    public void showNetLoading(String tip) {
        super.showWaitingDialog(tip);
    }

    @Override
    public void hideNetLoading() {
        super.hideWaitingDialog();
    }

    @Override
    public void setPresenter(ScanContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        zvScan.startSpot();
        UIUtil.showToast("扫描结果：" + result);
        mActivity.finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        UIUtil.showToast("打开相机出错");
    }

    @Override
    public void selectBottomOne(int selected) {
        ivScanCode.setImageResource(R.mipmap.hj_scan_scan_code_normal);
        ivCover.setImageResource(R.mipmap.hj_scan_cover_normal);
        ivVista.setImageResource(R.mipmap.hj_scan_vista_normal);
        ivTranslate.setImageResource(R.mipmap.hj_scan_translate_normal);
        switch (selected) {
            case 0:
                ivScanCode.setImageResource(R.mipmap.hj_scan_scan_code_press);
                break;
            case 1:
                ivCover.setImageResource(R.mipmap.hj_scan_cover_press);
                break;
            case 2:
                ivVista.setImageResource(R.mipmap.hj_scan_vista_press);
                break;
            case 3:
                ivTranslate.setImageResource(R.mipmap.hj_scan_translate_press);
                break;
            default:
                break;
        }
    }

    @Override
    public void showPopUpMenu() {
        if (flPopUp == null) {
            flPopUp = new FrameLayout(mActivity);
            flPopUp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            flPopUp.setBackgroundColor(UIUtil.getColor(R.color.hj_tools_white));
            TextView tv = new TextView(mActivity);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mActivity, 45));
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tv.setPadding(DisplayUtil.dip2px(mActivity,20), 0, 0 , 0);
            tv.setTextColor(UIUtil.getColor(R.color.hj_tools_gray0));
            tv.setTextSize(14);
            tv.setText("从相册选取二维码");
            flPopUp.addView(tv);

            RxView.clicks(tv).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    pwMenu.dismiss();
                    Intent intent = new Intent(mActivity, ImageGridActivity.class);
                    startActivityForResult(intent, IntentConst.IRCode.SCAN_IMAGE_PICKER);
                }
            });
        }
        pwMenu = PopupWindowFactory.getPopupWindowAtLocation(flPopUp, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        pwMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopupWindowFactory.makeWindowLight(mActivity);
            }
        });
        PopupWindowFactory.makeWindowDark(mActivity);
    }

    @Override
    public void vibrate() {
        Vibrator vibrator = (Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
