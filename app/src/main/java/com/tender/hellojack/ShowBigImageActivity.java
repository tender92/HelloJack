package com.tender.hellojack;

import android.os.Bundle;
import android.view.Menu;

import com.bm.library.PhotoView;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by boyu on 2017/11/6.
 */

public class ShowBigImageActivity extends BaseActivity {

    @BindView(R.id.photo_view)
    PhotoView pvHeader;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_show_big_image);
    }

    @Override
    protected void initToolbar() {
        updateTitle(UIUtil.getString(R.string.hj_photo_view_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(ShowBigImageActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        pvHeader.enable();
        String headerPath = PrefManager.getUserHeaderPath();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, pvHeader);
        } else {
            pvHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
