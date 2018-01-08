package com.tender.hellojack.business.setting.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;

public class AboutWeChatFragment extends BaseFragment implements AboutWeChatContract.View {

    private AboutWeChatContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_about_we_chat, container, false);

        return root;
    }

    @Override
    public void initUIData() {

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
    public void setPresenter(AboutWeChatContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }
}
