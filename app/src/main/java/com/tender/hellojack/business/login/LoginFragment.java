package com.tender.hellojack.business.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.home.HomeActivity;
import com.tender.hellojack.business.register.RegisterActivity;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.IntentConst;
import com.tender.tools.views.ClearEditText;
import com.tender.tools.views.dialog.SelectRegionDialog;
import com.tender.tools.views.dialog.WheelDialogCallBack;
import com.tender.tools.views.wheelview.WheelModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    private RelativeLayout rvRegion;
    private ClearEditText cetAccount, cetUserPwd;
    private ImageView ivPwdShow;
    private Button btnLogin;
    private TextView tvRegion, tvRegister;

    //地区选择相关
    private SelectRegionDialog regionDialog;
    private int mWheelSelectIndexRegion = 0;
    private List<WheelModel> regionData = new ArrayList<>();
    private static final String[] PLANETS = new String[]{
            "美国", "英国", "中国", "法国", "西班牙"};
    private static final int[] PLANETS_NATIONAL = new int[]{
            R.mipmap.hj_region_america, R.mipmap.hj_region_britain, R.mipmap.hj_region_china,
            R.mipmap.hj_region_france, R.mipmap.hj_region_spain};

    //显隐密码相关
    private boolean isPwdShow = false;
    private boolean pwdFirstShow = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < PLANETS.length; i++) {
            String kv = PLANETS[i];
            Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(PLANETS_NATIONAL[i])).getBitmap();
            regionData.add(new WheelModel(kv, bitmap, kv));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_login, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        rvRegion = (RelativeLayout) root.findViewById(R.id.rv_login_select_region);
        cetAccount = (ClearEditText) root.findViewById(R.id.cet_login_account);
        cetUserPwd = (ClearEditText) root.findViewById(R.id.cet_login_user_pwd);
        ivPwdShow = (ImageView) root.findViewById(R.id.iv_login_pwd_show);
        btnLogin = (Button) root.findViewById(R.id.btn_login_login);
        tvRegion = (TextView) root.findViewById(R.id.tv_login_region);
        tvRegister = (TextView) root.findViewById(R.id.tv_login_register);

        RxView.clicks(rvRegion).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showRegionDialog();
            }
        });
        RxView.clicks(ivPwdShow).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showOrHidePwd();
            }
        });
        RxView.clicks(btnLogin).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPresenter.login();
            }
        });
        RxView.clicks(tvRegister).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, RegisterActivity.class));
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
        Intent intent = mActivity.getIntent();
        if (intent != null) {
            cetAccount.setText(intent.getStringExtra(IntentConst.IRParam.REGISTER_ACCOUNT));
            cetUserPwd.setText(intent.getStringExtra(IntentConst.IRParam.REGISTER_PWD));
        }

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
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRegionDialog() {
        if (regionDialog == null) {
            regionDialog = new SelectRegionDialog(mActivity, mWheelSelectIndexRegion, true, regionData,
                    new WheelDialogCallBack() {
                        @Override
                        public void onCallback(Context context, String selectString) {
                            mWheelSelectIndexRegion = Integer.parseInt(selectString);
                            tvRegion.setText(PLANETS[mWheelSelectIndexRegion]);
                        }
                    });
        }
        regionDialog.show();
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
    public String getAccount() {
        return cetAccount.getText().toString();
    }

    @Override
    public String getUserPwd() {
        return cetUserPwd.getText().toString();
    }

    @Override
    public void goToHome() {
        startActivity(new Intent(mActivity, HomeActivity.class));
        mActivity.finish();
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
        }
    }
}
