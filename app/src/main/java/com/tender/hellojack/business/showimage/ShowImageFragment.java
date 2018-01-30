package com.tender.hellojack.business.showimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.string.StringUtil;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;

public class ShowImageFragment extends BaseFragment implements ShowImageContract.View {

    private ShowImageContract.Presenter mPresenter;

    private PhotoView pvHeader;

    private String account;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_show_image, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        pvHeader = (PhotoView) root.findViewById(R.id.photo_view);

        return root;
    }

    @Override
    public void initUIData() {
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
    public void showNetLoading(String tip) {
        super.showWaitingDialog(tip);
    }

    @Override
    public void hideNetLoading() {
        super.hideWaitingDialog();
    }

    @Override
    public void setPresenter(ShowImageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            mToolbar.setNavigationIcon(R.mipmap.hj_toolbar_back);
            mActivity.setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });

            mTitle.setText("头像");
        }
    }

    private void showHeader(String avatar) {
        if (StringUtil.hasValue(avatar)) {
            ImageLoaderUtil.loadLocalImage(avatar, pvHeader);
        } else {
            pvHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }
    }
}
