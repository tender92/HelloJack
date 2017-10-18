package com.tender.hellojack;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tender.hellojack.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by boyu on 2017/10/18.
 */

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.ll_myinfo_header)
    LinearLayout llHeader;
    @BindView((R.id.iv_myinfo_header))
    ImageView ivHeader;

    @OnClick({R.id.ll_myinfo_header, R.id.iv_myinfo_header})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_myinfo_header:
                showToast("选择图片");
                break;
            case R.id.iv_myinfo_header:
                showToast("近距离接触图片");
                break;
            default:
                break;
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_my_info);
    }

    @Override
    protected void initToolbar() {
        updateTitle("山中无老鼠，猴子称大王");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(MyInfoActivity.this);
    }
}
