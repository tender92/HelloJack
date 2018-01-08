package com.tender.hellojack.business.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.optionitemview.OptionItemView;
import com.tender.hellojack.*;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.mine.cardpacket.CardPacketActivity;
import com.tender.hellojack.business.mine.myfriends.MyFriendsActivity;
import com.tender.hellojack.business.mine.scan.ScanActivity;
import com.tender.hellojack.business.myinfo.MyInfoActivity;
import com.tender.hellojack.business.setting.SettingActivity;
import com.tender.hellojack.business.tasks.TasksActivity;
import com.tender.hellojack.business.webview.WebViewActivity;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.manager.threadpool.ThreadPoolFactory;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.AppConst;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.DialogUtil;
import com.tender.tools.utils.DisplayUtil;
import com.tender.tools.utils.UIUtil;
import com.tender.tools.utils.string.StringUtil;
import com.tender.umengshare.DataAnalyticsManager;

import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.functions.Action1;

public class MineFragment extends BaseFragment implements MineContract.View {

    private MineContract.Presenter mPresenter;

    private ImageView ivHeader, ivQRCode;
    private LinearLayout llMyInfo;
    private TextView tvName, tvAccount;
    private OptionItemView oivCardPacket, oivScan, oivShop, oivNearBy,
            oivMyFriends, oivSetting, oivTasks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRepository.getInstance().initRealm();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserRepository.getInstance().close();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_mine, container, false);
        ivHeader = (ImageView) root.findViewById(R.id.iv_mine_header);
        ivQRCode = (ImageView) root.findViewById(R.id.iv_mine_qrcode);
        llMyInfo = (LinearLayout) root.findViewById(R.id.ll_my_info);
        tvName = (TextView) root.findViewById(R.id.tv_mine_name);
        tvAccount = (TextView) root.findViewById(R.id.tv_mine_account);
        oivCardPacket = (OptionItemView) root.findViewById(R.id.oiv_mine_card_packet);
        oivScan = (OptionItemView) root.findViewById(R.id.oiv_mine_scan);
        oivShop = (OptionItemView) root.findViewById(R.id.oiv_mine_shop);
        oivNearBy = (OptionItemView) root.findViewById(R.id.oiv_mine_near_by);
        oivMyFriends = (OptionItemView) root.findViewById(R.id.oiv_mine_my_friends);
        oivSetting = (OptionItemView) root.findViewById(R.id.oiv_mine_setting);
        oivTasks = (OptionItemView) root.findViewById(R.id.oiv_mine_tasks);

        RxView.clicks(llMyInfo).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mActivity, MyInfoActivity.class);
                intent.putExtra(IntentConst.IRParam.MINE_ACCOUNT, mPresenter.getMineAccount());
                startActivity(intent);
            }
        });
        RxView.clicks(oivMyFriends).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, MyFriendsActivity.class));
            }
        });
        RxView.clicks(ivQRCode).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                DataAnalyticsManager.getInstance().onEvent(mActivity, "event_mine_show_qrcode");
                mPresenter.getMineInfo2();
            }
        });
        RxView.clicks(oivCardPacket).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, CardPacketActivity.class));
            }
        });
        RxView.clicks(oivScan).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, ScanActivity.class));
            }
        });
        RxView.clicks(oivSetting).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, SettingActivity.class));
            }
        });
        RxView.clicks(oivTasks).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, TasksActivity.class));
            }
        });
        RxView.clicks(oivShop).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mActivity, WebViewActivity.class);
                intent.putExtra(IntentConst.IRParam.WEB_VIEW_URL, AppConst.Url.MINE_SHOP_URL);
                intent.putExtra(IntentConst.IRParam.WEB_VIEW_TITLE, "购物");
                startActivity(intent);
            }
        });
        RxView.clicks(oivNearBy).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showMaterialDialog("提示", "查看附近的人功能将获取你的位置信息，你的位置信息会被保留一段时间。通过列表右上角的清除功能可随时手动清除位置信息。",
                        "确定", null, new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                hideMaterialDialog();
                            }
                        }, null);
            }
        });

        return root;
    }

    @Override
    public void initUIData() {
        mPresenter.createMine();
        mPresenter.getMineInfo();
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
    public void setPresenter(MineContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void showMyQRCode(UserInfo userInfo) {
        View root = View.inflate(mActivity, R.layout.hj_layout_qr_code_card, null);
        ImageView ivHeader = (ImageView) root.findViewById(R.id.iv_qrcode_card_header);
        ImageView ivGender = (ImageView) root.findViewById(R.id.iv_qrcode_gender);
        final ImageView ivQRCode = (ImageView) root.findViewById(R.id.iv_qrcode_card);
        TextView tvAccount = (TextView) root.findViewById(R.id.tv_qrcode_card_account);

        tvAccount.setText(userInfo.getAccount());
        ivGender.setImageResource(userInfo.getGender() == 1 ? R.mipmap.hj_gender_male : R.mipmap.hj_gender_female);
        String headerPath = userInfo.getAvatar();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, ivHeader);
        } else {
            ivHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }

        final String account = userInfo.getAccount();
        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap codeWithLogo5 = QRCodeEncoder.syncEncodeQRCode(account,
                        DisplayUtil.dip2px(mActivity, 200));

                UIUtil.postTaskSafely(new Runnable() {
                    @Override
                    public void run() {
                        ivQRCode.setImageBitmap(codeWithLogo5);
                    }
                });
            }
        });

        DialogUtil.CustomDialog dialog = new DialogUtil.CustomDialog(mActivity, 300, 400, root);
        dialog.show();
    }

    @Override
    public void showMineInfo(UserInfo userInfo) {
        String headerPath = userInfo.getAvatar();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, ivHeader);
        } else {
            ivHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }

        String account = userInfo.getAccount();
        tvAccount.setText(account);
        String name = userInfo.getName();
        if (StringUtil.hasValue(name)) {
            tvName.setText(name);
        } else {
            tvName.setText(account);
        }
    }
}
