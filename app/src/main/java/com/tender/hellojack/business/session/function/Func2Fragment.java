package com.tender.hellojack.business.session.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        llVoice = root.findViewById(R.id.ll_session_fun_voice);
        return root;
    }

    @Override
    protected void onBackPressed() {

    }
}
