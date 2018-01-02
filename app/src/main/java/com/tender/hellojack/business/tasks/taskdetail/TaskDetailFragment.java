package com.tender.hellojack.business.tasks.taskdetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.model.task.Task;
import com.tender.tools.IntentConst;
import com.tender.tools.views.dialog.HintDialog;

public class TaskDetailFragment extends BaseFragment implements TaskDetailContract.View {

    private TaskDetailContract.Presenter mPresenter;

    private EditText etTitle, etNote;
    private CheckBox cbIsDone;

    private long taskId;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_task_detail, container, false);
        etTitle = (EditText) root.findViewById(R.id.et_task_detail_title);
        etNote = (EditText) root.findViewById(R.id.et_task_detail_note);
        cbIsDone = (CheckBox) root.findViewById(R.id.cb_task_detail_is_done);

        cbIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            }
        });

        return root;
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

    @Override
    protected void onBackPressed() {

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
}
