package com.tender.hellojack.business.myinfo.qrcodecard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.manager.threadpool.ThreadPoolFactory;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.ui.DisplayUtil;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.ui.UIUtil;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by boyu on 2017/12/8.
 */

public class QRCodeCardFragment extends BaseFragment implements QRCodeCardContract.View {

    private QRCodeCardContract.Presenter mPresenter;

    private ImageView ivHeader, ivGender, ivQRCode;
    private TextView tvAccount, tvTip;

    private String account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_layout_qr_code_card, container, false);
        ivHeader = (ImageView) root.findViewById(R.id.iv_qrcode_card_header);
        ivGender = (ImageView) root.findViewById(R.id.iv_qrcode_gender);
        ivQRCode = (ImageView) root.findViewById(R.id.iv_qrcode_card);
        tvAccount = (TextView) root.findViewById(R.id.tv_qrcode_card_account);
        tvTip = (TextView) root.findViewById(R.id.tv_qrcode_tip);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = mActivity.getIntent();
        if (intent != null) {
            account = intent.getStringExtra(IntentConst.IRParam.MINE_ACCOUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void setPresenter(QRCodeCardContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initUIData() {
        mPresenter.getMineInfo(account);
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
    public void showMineInfo(UserInfo userInfo) {
        tvAccount.setText(account);
        ivGender.setImageResource(userInfo.getGender() == 1 ? R.mipmap.hj_gender_male : R.mipmap.hj_gender_female);
        tvTip.setText("扫一扫上面的二维码图案，加我微信");
        String headerPath = userInfo.getAvatar();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, ivHeader);
        } else {
            ivHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }

        final String mQRCodeStr = account;
        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap codeWithLogo5 = QRCodeEncoder.syncEncodeQRCode(mQRCodeStr,
                        DisplayUtil.dip2px(mActivity, 200));

                UIUtil.postTaskSafely(new Runnable() {
                    @Override
                    public void run() {
                        ivQRCode.setImageBitmap(codeWithLogo5);
                    }
                });
            }
        });
    }
}
