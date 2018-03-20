package com.tender.hellojack.business.tasks.listdetail;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.utils.ui.KeyBoardUtils;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by boyu on 2018/1/2.
 */

public class EditActionViewHolder {
    private EditText etTitle;
    private ImageView ivConfirm;

    private View itemView;
    private MenuItem menuItem;

    public EditActionViewHolder(View itemView, final MenuItem menuItem) {
        this.itemView = itemView;
        this.menuItem = menuItem;

        etTitle = (EditText) itemView.findViewById(com.tender.tools.R.id.et_edit_title_title);
        ivConfirm = (ImageView) itemView.findViewById(com.tender.tools.R.id.iv_edit_title_confirm);

        RxView.clicks(ivConfirm).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                menuItem.collapseActionView();
            }
        });
    }

    public void showCurrentTitle(CharSequence title) {
        etTitle.setText(title);
        etTitle.setSelection(title.length());
        etTitle.requestFocus();

        KeyBoardUtils.openKeyboard(etTitle, itemView.getContext());
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivConfirm.setEnabled(s.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public String getCurrentText() {
        KeyBoardUtils.closeSoftKeyboard(etTitle);
        return etTitle.getText().toString().trim();
    }
}
