package com.tender.hellojack.business.home.meterial.tablayout;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.tools.utils.ui.UIUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabLayoutFragment extends BaseFragment implements TabLayoutContract.View {

    private TabLayoutContract.Presenter mPresenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ViewPagerAdapter mAdapter;
    private static final String[] TITLE_LONG = new String[] {
            "深圳","南京","内蒙古呼和浩特","广西壮族自治区","上海","北京","天津"
    };

    private BottomSheetDialog mDialog;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_tab_layout, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        tabLayout = (TabLayout) root.findViewById(R.id.tl_tab_layout);
        viewPager = (ViewPager) root.findViewById(R.id.vp_tab_layout);
        return root;
    }

    @Override
    public void initUIData() {
        List<String> titleList = new ArrayList<>();
        Collections.addAll(titleList, TITLE_LONG);
        mAdapter = new ViewPagerAdapter(mActivity, titleList);
        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);
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
    public void setPresenter(TabLayoutContract.Presenter presenter) {
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

            mTitle.setText("详细资料");
        }
    }



    @Override
    public void showBottomSheetDialog() {
        if (mDialog == null) {
            mDialog = new BottomSheetDialog(mActivity);
            mDialog.setContentView(R.layout.hj_fragment_coordinator_bottom);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    UIUtil.showToast("BottomSheetDialog Closed");
                }
            });
        }
        mDialog.show();
    }

    @Override
    public void hideBottomSheetDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.hide();
        }
    }
}
