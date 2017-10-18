package com.tender.hellojack;

import android.os.Bundle;

import com.tender.hellojack.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_main);
    }

    @Override
    protected void initToolbar() {
        showLeftButton(false);
        showRightButton(true);
        updateTitle("MainActivity");
        clickRightButton(new Runnable() {
            @Override
            public void run() {
                showToast("你好我好大家好！");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
