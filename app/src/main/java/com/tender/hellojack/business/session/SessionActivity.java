package com.tender.hellojack.business.session;

import android.content.Intent;
import android.os.Bundle;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.ActivityUtils;

/**
 * Created by boyu
 */
public class SessionActivity extends BaseActivity {

    private SessionFragment contentFragment;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(IntentConst.IRParam.MY_FRIENDS_DISPLAY_NAME);
            updateTitle(title);
            showRightImage(true);
            mTitleRightImg.setImageResource(R.mipmap.hj_session_user);
            clickRightImage(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentFragment = (SessionFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new SessionFragment();
            new SessionPresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }

}