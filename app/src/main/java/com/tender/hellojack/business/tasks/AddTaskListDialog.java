package com.tender.hellojack.business.tasks;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.R;
import com.tender.hellojack.data.local.TasksRepository;
import com.tender.hellojack.utils.ScheduleProvider;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import rx.functions.Action1;

/**
 * Created by boyu on 2017/12/29.
 */

public class AddTaskListDialog extends BottomSheetDialogFragment {

    private Button btnCancel, btnOk;
    private TextInputEditText tietTitle;
    private TextInputLayout tilTitle;

    private int type = 1;//type = 1,添加任务列表; type = 2,添加任务
    private long listId = 0;//只有type = 2时有效

    public AddTaskListDialog(int type) {
        this.type = type;
    }

    public void setListId(long listId) {
        if (type == 2) {
            this.listId = listId;
        }
    }

    private BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN: {
                    dismiss();
                    break;
                }
                case BottomSheetBehavior.STATE_EXPANDED: {
                    break;
                }
                case BottomSheetBehavior.STATE_COLLAPSED: {
                    break;
                }

            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {

        View root = View.inflate(getContext(), R.layout.hj_layout_add_task_list, null);
        btnCancel = (Button) root.findViewById(R.id.btn_add_task_list_cancel);
        btnOk = (Button) root.findViewById(R.id.btn_add_task_list_ok);
        tietTitle = (TextInputEditText) root.findViewById(R.id.tiet_add_task_list_title);
        tilTitle = (TextInputLayout) root.findViewById(R.id.til_add_task_list_title);

        RxView.clicks(btnCancel).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                dismiss();
            }
        });
        RxView.clicks(btnOk).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (type == 1) {
                    addTaskList();
                } else if (type == 2) {
                    addTask();
                }
            }
        });
       tietTitle.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.length() == 0) {
                   tilTitle.setError("标题不能为空");
                   tilTitle.setErrorEnabled(true);
               } else {
                   tilTitle.setErrorEnabled(false);
               }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
       tietTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if (actionId == EditorInfo.IME_ACTION_DONE) {
                   addTaskList();
                   return true;
               }
               return false;
           }
       });

        dialog.setContentView(root);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) root.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = ((BottomSheetBehavior) behavior);
            bottomSheetBehavior.setBottomSheetCallback(callback);
        }

        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    private void addTaskList() {
        if (type == 2) {
            return;
        }
        String title = tietTitle.getText().toString();
        TasksRepository.getInstance().addNewList(title, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                dismiss();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                dismiss();
            }
        });
    }

    private void addTask() {
        if (type == 1) {
            return;
        }
        String title = tietTitle.getText().toString();
        TasksRepository.getInstance().addNewTaskToList(listId, title, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                dismiss();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                dismiss();
            }
        });
    }
}
