package com.tender.hellojack.business.home.meterial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;

public class CoordinatorLayoutFragment extends BaseFragment implements CoordinatorLayoutContract.View {

    private CoordinatorLayoutContract.Presenter mPresenter;
    private TextView tvContent;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_coordinator_layout, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.toolbar);
        tvContent = (TextView) root.findViewById(R.id.textView);
        return root;
    }

    @Override
    public void initUIData() {
        tvContent.setText(R.string.hj_text);
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
    public void setPresenter(CoordinatorLayoutContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("Material Design");
            mActivity.setSupportActionBar(mToolbar);
        }
    }
}
