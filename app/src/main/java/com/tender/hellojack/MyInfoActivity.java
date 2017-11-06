package com.tender.hellojack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.lqr.imagepicker.ui.ImageGridActivity;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.manager.PrefManager;
import com.tender.hellojack.utils.StringUtil;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.umengshare.DataAnalyticsManager;

import java.util.ArrayList;

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
                DataAnalyticsManager.getInstance().onEvent(MyInfoActivity.this, "event_myinfo_change_header");
                startActivityForResult(new Intent(MyInfoActivity.this, ImageGridActivity.class),
                    Const.IRCode.MY_INFO_IMAGE_PICKER);
                break;
            case R.id.iv_myinfo_header:
                DataAnalyticsManager.getInstance().onEvent(MyInfoActivity.this, "event_myinfo_show_header");
                startActivity(new Intent(MyInfoActivity.this, ShowBigImageActivity.class));
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

    @Override
    protected void onResume() {
        super.onResume();

        initHeader();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.IRCode.MY_INFO_IMAGE_PICKER &&
                resultCode == ImagePicker.RESULT_CODE_ITEMS) { //返回多张照片
            if (data != null) {
                ArrayList<ImageItem> imageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageList != null && imageList.size() > 0) {
                    String path = imageList.get(0).path;
                    PrefManager.setUserHeaderPath(path);
                    ImageLoaderUtil.loadLocalImage(path, ivHeader);
                }
            }
        }
    }

    private void initHeader() {
        String headerPath = PrefManager.getUserHeaderPath();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, ivHeader);
        }
    }
}
