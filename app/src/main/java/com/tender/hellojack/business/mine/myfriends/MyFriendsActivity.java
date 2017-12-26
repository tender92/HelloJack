package com.tender.hellojack.business.mine.myfriends;

import android.os.Bundle;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.utils.ActivityUtils;

/**
 * Created by boyu
 */
public class MyFriendsActivity extends BaseActivity {

    private MyFriendsFragment contentFragment;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
        updateTitle("通讯录");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentFragment = (MyFriendsFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new MyFriendsFragment();
            new MyFriendsPresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
    }

}