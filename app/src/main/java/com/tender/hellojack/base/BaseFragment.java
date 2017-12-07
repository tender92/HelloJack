package com.tender.hellojack.base;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.utils.App;
import com.tender.hellojack.utils.DialogUtil;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by boyu on 2017/12/7.
 */

abstract public class BaseFragment extends RxFragment implements IDialog, IToast {

    protected Activity mActivity;

    private DialogUtil.CustomDialog mWaitingDialog;

    protected abstract boolean onBackPressed();

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
        View root = View.inflate(mActivity, R.layout.hj_dialog_waiting, null);
        TextView tvTip = root.findViewById(R.id.tv_dialog_waiting_tip);
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
        DialogUtil.showHint(App.getAppContext(), content);
    }

    protected View getView(int resId) {
        return mActivity.findViewById(resId);
    }
}
