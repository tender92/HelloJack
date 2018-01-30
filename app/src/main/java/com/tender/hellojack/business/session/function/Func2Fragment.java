package com.tender.hellojack.business.session.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;

/**
 * Created by boyu on 2017/12/27.
 */

public class Func2Fragment extends BaseFragment {

    private LinearLayout llVoice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_session_func2, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        llVoice = (LinearLayout) root.findViewById(R.id.ll_session_fun_voice);
        return root;
    }

    @Override
    protected void initToolbar() {
        mToolbar.setVisibility(View.GONE);
    }
}
