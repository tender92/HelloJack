package com.tender.hellojack.business.myinfo.changesignature;

import android.os.Bundle;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.utils.ActivityUtils;

/**
 * Created by boyu
 */
public class ChangeSignatureActivity extends BaseActivity {

    private ChangeSignatureFragment contentFragment;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
        updateTitle("更改个性签名");
        showRightBtn(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentFragment = (ChangeSignatureFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
        if (contentFragment == null) {
            contentFragment = new ChangeSignatureFragment();
            new ChangeSignaturePresenter(Injection.provideRepository(), contentFragment, Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
        }
        clickRightBtn(contentFragment.clickBtnSaveSignature());
    }

}