package com.tender.hellojack.business.myinfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.lqr.imagepicker.ui.ImageGridActivity;
import com.tender.hellojack.Const;
import com.tender.hellojack.R;
import com.tender.hellojack.ShowBigImageActivity;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.manager.PrefManager;
import com.tender.hellojack.utils.DialogUtil;
import com.tender.hellojack.utils.Injection;
import com.tender.hellojack.utils.StringUtil;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.utils.ActivityUtils;
import com.tender.tools.views.dialog.SelectRegionDialog;
import com.tender.tools.views.dialog.WheelDialogCallBack;
import com.tender.tools.views.wheelview.WheelModel;
import com.tender.umengshare.DataAnalyticsManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by boyu on 2017/10/18.
 */

public class MyInfoActivity extends BaseActivity {

    private MyInfoFragment contentFragment;



    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
        updateTitle("个人信息");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentFragment = (MyInfoFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new MyInfoFragment();
            new MyInfoPresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }
}
