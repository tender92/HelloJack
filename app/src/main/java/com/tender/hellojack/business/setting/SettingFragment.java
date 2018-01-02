package com.tender.hellojack.business.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.optionitemview.OptionItemView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.setting.newmessage.NewMessageNotifyActivity;
import com.tender.hellojack.utils.ScheduleProvider;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class SettingFragment extends BaseFragment implements SettingContract.View {

    private SettingContract.Presenter mPresenter;

    private OptionItemView oivNewMessage, oivNoDisturb, oivChat, oivPrivacy,
        oivCommon,oivAccountAndSafety, oivAbout, oivExit;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_setting, container, false);
        oivNewMessage = (OptionItemView) root.findViewById(R.id.oiv_setting_new_message);
        oivNoDisturb = (OptionItemView) root.findViewById(R.id.oiv_setting_no_disturb);
        oivChat = (OptionItemView) root.findViewById(R.id.oiv_setting_chat);
        oivPrivacy = (OptionItemView) root.findViewById(R.id.oiv_setting_privacy);
        oivCommon = (OptionItemView) root.findViewById(R.id.oiv_setting_common);
        oivAccountAndSafety = (OptionItemView) root.findViewById(R.id.oiv_setting_account_and_safety);
        oivAbout = (OptionItemView) root.findViewById(R.id.oiv_setting_about);
        oivExit = (OptionItemView) root.findViewById(R.id.oiv_setting_exit);

        RxView.clicks(oivNewMessage).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, NewMessageNotifyActivity.class));
            }
        });
        return root;
    }

    @Override
    public void initUIData() {

    }

    @Override
    public void showNetLoading(String tip) {
        super.showWaitingDialog(tip);
    }

    @Override
    public void hideNetLoading() {
        super.hideWaitingDialog();
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }
}
