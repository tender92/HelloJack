package com.tender.hellojack.base;

import android.view.View;

/**
 * Created by boyu on 2017/10/19.
 */

public interface IDialog {

    void showWaitingDialog(String tip);

    void hideWaitingDialog();

    void showMaterialDialog(String tip, String message, String positiveText, String negativeText, View.OnClickListener positiveBtnClickListener, View.OnClickListener negativeBtnClickListener);

    void hideMaterialDialog();
}
