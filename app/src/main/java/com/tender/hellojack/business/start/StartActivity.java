package com.tender.hellojack.business.start;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;

import com.tender.hellojack.Const;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.manager.PrefManager;
import com.tender.hellojack.utils.DialogUtil;
import com.tender.tools.utils.ActivityUtils;
import com.tender.tools.utils.DisplayUtil;

/**
 * Created by boyu on 2017/12/7.
 */

public class StartActivity extends BaseActivity {

    private StartFragment contentFragment;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
        hideToolbar();
        View view = findViewById(R.id.hj_view_fill_up);
        view.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        PrefManager.saveScreenWidth(metrics.widthPixels - 2 * DisplayUtil.dip2px(this, 20));
        PrefManager.saveScreenHeight(metrics.heightPixels);

        contentFragment = (StartFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new StartFragment();
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }

    @Override
    public void onBackPressed() {
        contentFragment.onBackPressed();
        super.onBackPressed();
    }
}
