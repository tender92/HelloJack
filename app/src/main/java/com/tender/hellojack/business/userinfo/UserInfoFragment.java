package com.tender.hellojack.business.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import com.tender.hellojack.model.contact.UserInfo;
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

    private String userAccount;
    private String userDisplayName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = mActivity.getIntent();
        if (intent != null) {
            userAccount = intent.getStringExtra(IntentConst.IRParam.MY_FRIENDS_ACCOUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_user_info, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
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
                intent.putExtra(IntentConst.IRParam.MY_FRIENDS_ACCOUNT, userAccount);
                intent.putExtra(IntentConst.IRParam.MY_FRIENDS_DISPLAY_NAME, userDisplayName);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void initUIData() {
        mPresenter.getUserInfo(userAccount);
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
    public void showUserInfo(UserInfo userInfo) {
        if (StringUtil.hasValue(userInfo.getAvatar())) {
            ImageLoaderUtil.loadLocalImage(userInfo.getAvatar(), ivHeader);
        } else {
            ivHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }
        if (userInfo.getGender() == 1) {
            ivGender.setImageResource(R.mipmap.hj_gender_male);
        } else if (userInfo.getGender() == 0) {
            ivGender.setImageResource(R.mipmap.hj_gender_female);
        } else {
            ivGender.setVisibility(View.GONE);
        }
        userDisplayName = userInfo.getDisplayName();
        tvAlias.setText(userDisplayName);
        tvAccount.setText(userInfo.getAccount());
        tvName.setText(userInfo.getName());
        oivRegion.setRightText(userInfo.getRegion());
        oivSignature.setRightText(userInfo.getSignature());
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            mToolbar.setNavigationIcon(R.mipmap.hj_toolbar_back);
            mActivity.setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });

            mTitle.setText("详细资料");
        }
    }
}
