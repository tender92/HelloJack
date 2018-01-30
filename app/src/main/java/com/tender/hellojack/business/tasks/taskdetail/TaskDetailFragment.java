package com.tender.hellojack.business.tasks.taskdetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.tasks.listdetail.EditActionViewHolder;
import com.tender.hellojack.model.task.Task;
import com.tender.tools.IntentConst;
import com.tender.tools.views.dialog.HintDialog;

public class TaskDetailFragment extends BaseFragment implements TaskDetailContract.View {

    private TaskDetailContract.Presenter mPresenter;

    private EditText etTitle, etNote;
    private CheckBox cbIsDone;

    private long taskId;

    private EditActionViewHolder editViewHolder;

    private int modle = 0;//0 待编辑； 1 编辑完成

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_task_detail, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        etTitle = (EditText) root.findViewById(R.id.et_task_detail_title);
        etNote = (EditText) root.findViewById(R.id.et_task_detail_note);
        cbIsDone = (CheckBox) root.findViewById(R.id.cb_task_detail_is_done);

        cbIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(com.tender.tools.R.menu.hj_tools_menu_edit_and_delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == com.tender.tools.R.id.menu_delete) {
            deleteTask();
            return true;
        } else if (itemId == com.tender.tools.R.id.menu_edit) {
            if (modle == 0) {
                modle = 1;
                item.setIcon(com.tender.tools.R.mipmap.hj_tools_done_black);
                onEditTask();
            } else if (modle == 1){
                modle = 0;
                item.setIcon(com.tender.tools.R.mipmap.hj_tools_edit_black);
                onEditTaskDone();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initUIData() {
        Intent intent = mActivity.getIntent();
        if (intent != null) {
            taskId = intent.getLongExtra(IntentConst.IRParam.TASK_ITEM_ID, 0);
        }

        mPresenter.getTask(taskId);
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
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void onEditTask() {
        etTitle.setEnabled(true);
        etNote.setEnabled(true);
        cbIsDone.setEnabled(true);

        etTitle.setSelection(etTitle.getText().toString().length());
        etTitle.requestFocus();
    }

    public void onEditTaskDone() {
        String taskTitle = etTitle.getText().toString().trim();
        String taskNote = etNote.getText().toString().trim();
        boolean isDone = cbIsDone.isChecked();
        mPresenter.updateTask(taskId, taskTitle, taskNote, isDone);
    }

    public void deleteTask() {
        new HintDialog.Builder(mActivity)
                .setTitle("删除这个任务")
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteTask(taskId);
                    }
                })
                .setNegativeButton("取消", null)
                .show(getChildFragmentManager(), "HintDialog");
    }

    @Override
    public void showTask(Task task) {
        etTitle.setText(task.getTitle());
        etNote.setText(task.getNote());
        cbIsDone.setChecked(task.isDone());

        etTitle.setEnabled(false);
        etNote.setEnabled(false);
        cbIsDone.setEnabled(false);
    }

    @Override
    public void finish() {
        mActivity.finish();
    }

    @Override
    protected void initToolbar() {
        Intent intent = mActivity.getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(IntentConst.IRParam.TASK_ITEM_TITLE);
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

                mTitle.setText(title);
            }
        }

    }
}
