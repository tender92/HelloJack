package com.tender.hellojack;

import com.tender.hellojack.base.BaseActivity;

/**
 * Created by boyu on 2017/10/20.
 */

public class HomeActivity extends BaseActivity {
    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_home);
    }

    @Override
    protected void initToolbar() {

        updateTitle("占山为王");
    }
}
