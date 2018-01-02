package com.tender.hellojack.base;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.tools.utils.DialogUtil;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.UIUtil;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by boyu on 2017/12/7.
 */

abstract public class BaseFragment extends RxFragment implements IDialog, IToast {

    protected Activity mActivity;

    private DialogUtil.CustomDialog mWaitingDialog;
    private MaterialDialog mMaterialDialog;

    protected abstract void onBackPressed();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            hideWaitingDialog();
        }
    }

    @Override
    public void showWaitingDialog(String tip) {
        hideWaitingDialog();
        View root = View.inflate(mActivity, R.layout.hj_tools_dialog_waiting, null);
        TextView tvTip = (TextView) root.findViewById(R.id.tv_dialog_waiting_tip);
        tvTip.setText(tip);
        mWaitingDialog = new DialogUtil.CustomDialog(mActivity, root);
        mWaitingDialog.show();
        mWaitingDialog.setCancelable(false);
    }

    @Override
    public void hideWaitingDialog() {
        if (mWaitingDialog != null) {
            mWaitingDialog.dismiss();
            mWaitingDialog = null;
        }
    }

    @Override
    public void showToast(String content) {
        UIUtil.showToast(content);
    }

    @Override
    public void showMaterialDialog(String tip, String message, String positiveText, String negativeText, View.OnClickListener positiveBtnClickListener, View.OnClickListener negativeBtnClickListener) {
        hideMaterialDialog();
        mMaterialDialog = new MaterialDialog(mActivity);
        if (StringUtil.hasValue(tip)) {
            mMaterialDialog.setTitle(tip);
        }
        if (StringUtil.hasValue(message)) {
            mMaterialDialog.setMessage(message);
        }
        if (StringUtil.hasValue(positiveText)) {
            mMaterialDialog.setPositiveButton(positiveText, positiveBtnClickListener);
        }
        if (StringUtil.hasValue(negativeText)) {
            mMaterialDialog.setNegativeButton(negativeText, negativeBtnClickListener);
        }
        mMaterialDialog.show();
    }

    @Override
    public void hideMaterialDialog() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }

    protected View getView(int resId) {
        return mActivity.findViewById(resId);
    }

    protected final  <T> LifecycleTransformer<T> bindEvent(FragmentEvent event) {
        return this.<T>bindUntilEvent(event);
    }
}
