package com.tender.hellojack.business.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.login.LoginActivity;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.IntentConst;
import com.tender.tools.views.ClearEditText;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    private RegisterContract.Presenter mPresenter;
    private ClearEditText cetUserAccount, cetUserName, cetUserPwd;
    private Button btnRegister;
    private ImageView ivPwdShow;

    //显隐密码相关
    private boolean isPwdShow = false;
    private boolean pwdFirstShow = true;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_register, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        cetUserAccount = (ClearEditText) root.findViewById(R.id.cet_register_user_account);
        cetUserName = (ClearEditText) root.findViewById(R.id.cet_register_user_name);
        cetUserPwd = (ClearEditText) root.findViewById(R.id.cet_register_user_pwd);
        btnRegister = (Button) root.findViewById(R.id.btn_register_register);
        ivPwdShow = (ImageView) root.findViewById(R.id.iv_register_pwd_show);

        RxView.clicks(btnRegister).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPresenter.register();
            }
        });
        RxView.clicks(ivPwdShow).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showOrHidePwd();
            }
        });
        return root;
    }

    @Override
    public void initUIData() {
        cetUserPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pwdFirstShow = false;
            }
        });
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
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showOrHidePwd() {
        if (pwdFirstShow) {
            pwdFirstShow = false;
            cetUserPwd.setText("");
        }
        if (isPwdShow) {
            isPwdShow = false;
            cetUserPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivPwdShow.setImageResource(R.mipmap.hj_login_pwd_hide);
        } else {
            isPwdShow = true;
            cetUserPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivPwdShow.setImageResource(R.mipmap.hj_login_pwd_show);
        }
    }

    @Override
    public String getUserAccount() {
        return cetUserAccount.getText().toString();
    }

    @Override
    public String getUserName() {
        return cetUserName.getText().toString();
    }

    @Override
    public String getUserPwd() {
        return cetUserPwd.getText().toString();
    }

    @Override
    public void goToLogin(String account, String pwd) {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        intent.putExtra(IntentConst.IRParam.REGISTER_ACCOUNT, account);
        intent.putExtra(IntentConst.IRParam.REGISTER_PWD, pwd);
        startActivity(intent);
        mActivity.finish();
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

            mTitle.setText("注册账号");
        }
    }
}
