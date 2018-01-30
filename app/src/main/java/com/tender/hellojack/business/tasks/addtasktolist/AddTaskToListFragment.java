package com.tender.hellojack.business.tasks.addtasktolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.tasks.AddTaskListDialog;
import com.tender.hellojack.utils.ScheduleProvider;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class AddTaskToListFragment extends BaseFragment implements AddTaskToListContract.View {

    private AddTaskToListContract.Presenter mPresenter;

    private TextInputLayout tilTask;
    private TextInputEditText tietTask;
    private TextView tvListTitle;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_add_task_to_list, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        tilTask = (TextInputLayout) root.findViewById(R.id.til_add_task_to_list_task);
        tietTask = (TextInputEditText) root.findViewById(R.id.tiet_add_task_to_list_task);
        tvListTitle = (TextView) root.findViewById(R.id.tv_add_task_to_list_title);

        RxView.clicks(tvListTitle).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPresenter.selectTaskList();
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(com.tender.tools.R.menu.hj_tools_menu_confirm, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (android.R.id.home == itemId) {
            mActivity.finish();
        } else if (com.tender.tools.R.id.menu_confirm == itemId) {
            addTaskList();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initUIData() {
        tietTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tilTask.setError("标题不能为空");
                    tilTask.setErrorEnabled(true);

                } else {
                    tilTask.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
    public void setPresenter(AddTaskToListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void addTaskList() {
        String taskTitle = tietTask.getText().toString();
        mPresenter.addTaskList(taskTitle);
    }

    @Override
    public void showSelectedList(String title) {
        tvListTitle.setText(title);
        String hint = String.format("添加一个任务到\"%1s\"至列表", title);
        tietTask.setHint(hint);
    }

    @Override
    public void showAddNewListHint() {
        tvListTitle.setText("创建一个任务列表...");
        tietTask.setHint("添加一个任务");
    }

    @Override
    public void showSelectListDialog() {
        SelectListDialog dialog = new SelectListDialog();
        dialog.setArguments(mActivity, new SelectListDialog.CallBack() {
            @Override
            public void onSelectList(long listId, String title) {
                mPresenter.OnTaskListSelected(listId, title);
            }
        });
        dialog.show(getChildFragmentManager(), "SelectListDialog");
    }

    @Override
    public void showAddNewListDialog() {
        AddTaskListDialog dialog = new AddTaskListDialog(1);
        dialog.show(getChildFragmentManager(), "AddTaskListDialog");
    }

    @Override
    public void finishActivity() {
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

            mTitle.setText("添加任务列表");
        }
    }
}
