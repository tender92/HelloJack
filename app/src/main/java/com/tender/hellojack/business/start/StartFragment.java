package com.tender.hellojack.business.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tender.hellojack.R;
import com.tender.hellojack.business.home.HomeActivity;
import com.tender.tools.TenderLog;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by boyu on 2017/12/7.
 */

public class StartFragment extends RxFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_start, container, false);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void delayToHome() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            TenderLog.e(e.getMessage());
        }
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public boolean onBackPressed() {
        return false;
    }

}
