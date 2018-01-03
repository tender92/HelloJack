package com.tender.hellojack;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.bm.library.PhotoView;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.data.local.TasksRepository;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.IntentConst;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;

/**
 * Created by boyu on 2017/11/6.
 */

public class ShowBigImageActivity extends BaseActivity {

    @BindView(R.id.photo_view)
    PhotoView pvHeader;

    private String account;

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

        Intent intent = getIntent();
        if (intent != null) {
            account = intent.getStringExtra(IntentConst.IRParam.MINE_ACCOUNT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        pvHeader.enable();
        final UserInfo userInfo = UserRepository.getInstance().getUser(account).get(0);
        userInfo.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                showHeader(userInfo.getAvatar());
            }
        });
        showHeader(userInfo.getAvatar());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void showHeader(String avatar) {
        if (StringUtil.hasValue(avatar)) {
            ImageLoaderUtil.loadLocalImage(avatar, pvHeader);
        } else {
            pvHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }
    }
}
