package com.tender.hellojack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.manager.PrefManager;
import com.tender.hellojack.utils.StringUtil;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by boyu on 2017/10/18.
 */

public class MineActivity extends BaseActivity {

    @BindView(R.id.iv_mine_header)
    ImageView ivHeader;
    @BindView(R.id.ll_my_info)
    LinearLayout llMyInfo;

    @OnClick(R.id.ll_my_info)
    public void click(View view) {
        showToast("Show Time!");
        startActivity(new Intent(MineActivity.this, MyInfoActivity.class));
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activty_mine);
    }

    @Override
    protected void initToolbar() {
        updateTitle("我的地盘我做主");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(MineActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initHeader();
    }

    private void initHeader() {
        String headerPath = PrefManager.getUserHeaderPath();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.LoadLocalImage(headerPath, ivHeader);
        }
    }
}
