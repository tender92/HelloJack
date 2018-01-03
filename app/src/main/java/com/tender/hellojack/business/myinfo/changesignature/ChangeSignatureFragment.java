package com.tender.hellojack.business.myinfo.changesignature;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.tools.IntentConst;
import com.tender.tools.TenderLog;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.string.StringUtil;

public class ChangeSignatureFragment extends BaseFragment implements ChangeSignatureContract.View {

    private ChangeSignatureContract.Presenter mPresenter;

    private EditText etSignature;
    private TextView tvCount;

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
        View root = inflater.inflate(R.layout.hj_fragment_change_signature, container, false);
        etSignature = (EditText) root.findViewById(R.id.et_change_signature);
        tvCount = (TextView) root.findViewById(R.id.tv_change_signature_Count);
        return root;
    }

    @Override
    public void initUIData() {
        mPresenter.getMineInfo(account);

        initListener();
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
    public void setPresenter(ChangeSignatureContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }

    private void initListener() {
        etSignature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String contentStr = editable.toString();
                try {
                    if (contentStr.length() > 20) {
                        contentStr = contentStr.substring(0, 20);
                        etSignature.setText(contentStr);
                        etSignature.setSelection(20);
                    }
                    tvCount.setText(String.valueOf(20 - contentStr.length()));
                } catch (Exception e) {
                    TenderLog.d(e.getMessage());
                }
            }
        });
    }

    public Runnable clickBtnSaveSignature() {
        return new Runnable() {
            @Override
            public void run() {
                String signature = etSignature.getText().toString();
                if (StringUtil.hasValue(signature.trim())) {
                    mPresenter.updateUserSignature(account, signature);
                    showToast("保存成功");
                    mActivity.finish();
                } else {
                    showMaterialDialog("提示", "没有输入个性签名，请重新填写", "确定", "", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hideMaterialDialog();
                        }
                    }, null);
                }
            }
        };
    }

    @Override
    public void showMineSignature(String signature) {
        etSignature.setText(signature);
        etSignature.setSelection(signature.length());
        etSignature.requestFocus();
        tvCount.setText(String.valueOf(20 - signature.length()));
    }
}
