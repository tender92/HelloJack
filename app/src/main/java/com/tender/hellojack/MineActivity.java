package com.tender.hellojack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.business.myinfo.MyInfoActivity;
import com.tender.hellojack.manager.PrefManager;
import com.tender.hellojack.manager.threadpool.ThreadPoolFactory;
import com.tender.hellojack.utils.App;
import com.tender.hellojack.utils.DialogUtil;
import com.tender.hellojack.utils.StringUtil;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.utils.DisplayUtil;
import com.tender.tools.views.MyPopupWindow;
import com.tender.umengshare.DataAnalyticsManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by boyu on 2017/10/18.
 */

public class MineActivity extends BaseActivity {

    @BindView(R.id.iv_mine_header)
    ImageView ivHeader;
    @BindView(R.id.ll_my_info)
    LinearLayout llMyInfo;
    @BindView(R.id.iv_mine_qrcode)
    ImageView ivQRCode;
    @BindView(R.id.tv_mine_account)
    TextView tvAccount;

    @OnClick({R.id.ll_my_info, R.id.iv_mine_qrcode})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_my_info:
                startActivity(new Intent(MineActivity.this, MyInfoActivity.class));
                break;
            case R.id.iv_mine_qrcode:
                DataAnalyticsManager.getInstance().onEvent(MineActivity.this, "event_mine_show_qrcode");
                showMyQRCode();
                break;
            default:
                break;
        }

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activty_mine);
    }

    @Override
    protected void initToolbar() {
        updateTitle("我的地盘我做主");
        showRightButton(true);
        clickRightButton(new Runnable() {
            @Override
            public void run() {
                MyPopupWindow popupWindow = new MyPopupWindow(MineActivity.this);
                popupWindow.setContentView(LayoutInflater.from(MineActivity.this).inflate(R.layout.hj_layout_select_status, null));
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(MineActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initUserData();
    }

    private void initUserData() {
        String headerPath = PrefManager.getUserHeaderPath();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, ivHeader);
        }

        String account = PrefManager.getUserAccount();
        tvAccount.setText(account);
    }

    private void showMyQRCode() {
        View root = View.inflate(this, R.layout.hj_layout_qrcode_card, null);
        ImageView ivHeader = root.findViewById(R.id.iv_qrcode_card_header);
        final ImageView ivQRCode = root.findViewById(R.id.iv_qrcode_card);
        TextView tvAccount = root.findViewById(R.id.tv_qrcode_card_account);

        tvAccount.setText(PrefManager.getUserAccount());
        String headerPath = PrefManager.getUserHeaderPath();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, ivHeader);
        } else {
            ivHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }

        final String mQRCodeStr = PrefManager.getUserAccount();
        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap codeWithLogo5 = QRCodeEncoder.syncEncodeQRCode(mQRCodeStr,
                        DisplayUtil.dip2px(MineActivity.this, 200));

                App.postTaskSafely(new Runnable() {
                    @Override
                    public void run() {
                        ivQRCode.setImageBitmap(codeWithLogo5);
                    }
                });
            }
        });

        DialogUtil.CustomDialog dialog = new DialogUtil.CustomDialog(this, 300, 400, root);
        dialog.show();
    }
}
