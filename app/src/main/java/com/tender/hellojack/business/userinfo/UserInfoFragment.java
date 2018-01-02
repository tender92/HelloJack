package com.tender.hellojack.business.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.optionitemview.OptionItemView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.session.SessionActivity;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.model.enums.GenderEnum;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.string.StringUtil;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class UserInfoFragment extends BaseFragment implements UserInfoContract.View {

    private UserInfoContract.Presenter mPresenter;

    private ImageView ivHeader, ivGender;
    private TextView tvAlias, tvAccount, tvName;
    private OptionItemView oivRegion, oivSignature;
    private Button btnSendMessage;

    private UserInfo currentUser = null;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_user_info, container, false);
        ivHeader = (ImageView) root.findViewById(R.id.iv_user_info_header);
        ivGender = (ImageView) root.findViewById(R.id.iv_user_info_gender);
        tvAlias = (TextView) root.findViewById(R.id.tv_user_info_alias);
        tvAccount = (TextView) root.findViewById(R.id.tv_user_info_account);
        tvName = (TextView) root.findViewById(R.id.tv_user_info_name);
        oivRegion = (OptionItemView) root.findViewById(R.id.oiv_user_info_region);
        oivSignature = (OptionItemView) root.findViewById(R.id.oiv_user_info_signature);
        btnSendMessage = (Button) root.findViewById(R.id.btn_user_info_send_message);

        RxView.clicks(btnSendMessage).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mActivity, SessionActivity.class);
                intent.putExtra(IntentConst.IRParam.USER_INFO_USER, currentUser);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void initUIData() {
        mPresenter.handleIntentParams(mActivity.getIntent());
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
    public void setPresenter(UserInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void showUserInfo(UserInfo userInfo) {
        currentUser = userInfo;
        if (StringUtil.hasValue(userInfo.avatar)) {
            ImageLoaderUtil.loadLocalImage(userInfo.avatar, ivHeader);
        } else {
            ivHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }
        if (userInfo.gender == GenderEnum.MALE) {
            ivGender.setImageResource(R.mipmap.hj_gender_male);
        } else if (userInfo.gender == GenderEnum.FEMALE) {
            ivGender.setImageResource(R.mipmap.hj_gender_female);
        } else {
            ivGender.setVisibility(View.GONE);
        }
        tvAlias.setText(userInfo.displayName);
        tvAccount.setText(userInfo.account);
        tvName.setText(userInfo.name);
        oivRegion.setRightText(userInfo.region);
        oivSignature.setRightText(userInfo.signature);
    }
}
