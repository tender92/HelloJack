package com.tender.hellojack.business.myinfo.changename;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.tools.IntentConst;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.views.ClearEditText;

public class ChangeNameFragment extends BaseFragment implements ChangeNameContract.View {

    private ChangeNameContract.Presenter mPresenter;

    private ClearEditText cetName;

    private String account;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if (intent != null) {
            account = intent.getStringExtra(IntentConst.IRParam.MINE_ACCOUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_change_name, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        cetName = (ClearEditText) root.findViewById(R.id.cet_change_name_name);
        return root;
    }

    @Override
    public void initUIData() {
        mPresenter.getMineInfo(account);
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
    public void setPresenter(ChangeNameContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMineName(String name) {
        cetName.setText(name);
        cetName.setSelection(0, cetName.getText().length());
        cetName.requestFocus();
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

            Button btnRight = (Button) mToolbar.findViewById(R.id.btn_actionbar_right);
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userName = cetName.getText().toString();
                    if (StringUtil.hasValue(userName.trim())) {
                        mPresenter.updateUserName(account, userName);
                        showToast("保存成功");
                        mActivity.finish();
                    } else {
                        showMaterialDialog("提示", "没有输入昵称，请重新填写", "确定", "", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hideMaterialDialog();
                            }
                        }, null);
                    }
                }
            });

            mTitle.setText("更改名字");
        }
    }
}
