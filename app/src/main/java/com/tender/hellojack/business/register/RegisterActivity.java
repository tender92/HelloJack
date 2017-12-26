package com.tender.hellojack.business.register;

import android.os.Bundle;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.utils.ActivityUtils;

/**
 * Created by boyu
 */
public class RegisterActivity extends BaseActivity {

    private RegisterFragment contentFragment;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
        updateTitle("注册账号");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentFragment = (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new RegisterFragment();
            new RegisterPresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }

}