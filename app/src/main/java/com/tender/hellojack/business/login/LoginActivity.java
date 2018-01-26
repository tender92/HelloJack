package com.tender.hellojack.business.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.utils.ActivityUtils;
import com.tender.tools.utils.ui.DialogUtil;
import com.tender.tools.utils.ui.UIUtil;

/**
 * Created by boyu
 */
public class LoginActivity extends BaseActivity {

    private LoginFragment contentFragment;

    private View vFillUp;

    private boolean isExit = false;
    private Handler mHandler = new Handler();

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vFillUp = findViewById(R.id.hj_view_fill_up);
        vFillUp.setVisibility(View.GONE);
        hideToolbar();

        contentFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new LoginFragment();
            new LoginPresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            Intent i= new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        } else {
            isExit = true;
            DialogUtil.showHint(UIUtil.getAppContext(), "再按一次退出程序");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 3000);
        }
    }
}