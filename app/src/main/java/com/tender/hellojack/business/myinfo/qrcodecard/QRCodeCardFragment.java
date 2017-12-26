package com.tender.hellojack.business.myinfo.qrcodecard;

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
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.DisplayUtil;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.UIUtil;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by boyu on 2017/12/8.
 */

public class QRCodeCardFragment extends BaseFragment implements QRCodeCardContract.View {

    private QRCodeCardContract.Presenter mPresenter;

    private ImageView ivHeader, ivGender, ivQRCode;
    private TextView tvAccount, tvTip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_layout_qr_code_card, container, false);
        ivHeader = root.findViewById(R.id.iv_qrcode_card_header);
        ivGender = root.findViewById(R.id.iv_qrcode_gender);
        ivQRCode = root.findViewById(R.id.iv_qrcode_card);
        tvAccount = root.findViewById(R.id.tv_qrcode_card_account);
        tvTip = root.findViewById(R.id.tv_qrcode_tip);
        return root;
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
        tvAccount.setText(PrefManager.getUserAccount());
        ivGender.setImageResource(PrefManager.getUserGender() == 1 ? R.mipmap.hj_gender_male : R.mipmap.hj_gender_female);
        tvTip.setText("扫一扫上面的二维码图案，加我微信");
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

    @Override
    public void showNetLoading(String tip) {
        super.showWaitingDialog(tip);
    }

    @Override
    public void hideNetLoading() {
        super.hideWaitingDialog();
    }
}
