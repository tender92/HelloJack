package com.tender.hellojack.business.tasks.listdetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.recyclerview.LQRRecyclerView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.tasks.AddTaskListDialog;
import com.tender.hellojack.business.tasks.taskdetail.TaskDetailActivity;
import com.tender.hellojack.model.task.Task;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.DisplayUtil;
import com.tender.tools.views.dialog.HintDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class ListDetailFragment extends BaseFragment implements ListDetailContract.View {

    private ListDetailContract.Presenter mPresenter;

    private LQRRecyclerView rvDoneTasks;
    private TextView tvHeader;
    private LinearLayout llFooter;

    private LQRAdapterForRecyclerView mAdapter;

    private long listId;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_list_detail, container, false);
        rvDoneTasks = (LQRRecyclerView) root.findViewById(R.id.rv_list_detail_done_list);

        tvHeader = new TextView(mActivity);
        tvHeader.setText("列表任务集");
        tvHeader.setTextSize(20);
        tvHeader.setPadding(DisplayUtil.sp2px(mActivity, 15), 0, 0, 0);
        ViewGroup.LayoutParams hParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mActivity, 48));
        tvHeader.setGravity(Gravity.CENTER_VERTICAL);
        tvHeader.setLayoutParams(hParams);

        llFooter = (LinearLayout) ViewGroup.inflate(mActivity, R.layout.hj_layout_tasks_list_footer, null);
        TextView tv = (TextView) llFooter.findViewById(R.id.tv_tasks_list_footer);
        tv.setText("添加一个任务");
        RxView.clicks(llFooter).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                AddTaskListDialog dialog = new AddTaskListDialog(2);
                dialog.setListId(listId);
                dialog.show(getChildFragmentManager(), "AddTaskListDialog");
            }
        });
        return root;
    }

    @Override
    public void initUIData() {
        final Intent intent = mActivity.getIntent();
        if (intent != null) {
            listId = intent.getLongExtra(IntentConst.IRParam.List_ITEM_ID, 0);
        }

        final List<Task> tasks = mPresenter.getTasksByListId(listId);
        mAdapter = new LQRAdapterForRecyclerView<Task>(mActivity,R.layout.hj_layout_task_detail_item, tasks) {
            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final Task item, final int position) {
                ((CheckBox)helper.getView(R.id.cb_list_detail_item)).setChecked(item.isDone());
                ((TextView)helper.getView(R.id.tv_list_detail_task_title)).setText(item.getTitle());
                ((TextView)helper.getView(R.id.tv_list_detail_task_note)).setText(item.getNote());

                RxView.clicks(helper.getView(R.id.ll_list_detail_item)).throttleFirst(1, TimeUnit.SECONDS)
                        .observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent1 = new Intent(mActivity, TaskDetailActivity.class);
                        intent1.putExtra(IntentConst.IRParam.TASK_ITEM_TITLE, item.getTitle());
                        intent1.putExtra(IntentConst.IRParam.TASK_ITEM_ID, item.getId());
                        startActivity(intent1);
                    }
                });
            }
        };

        mAdapter.addHeaderView(tvHeader);
        mAdapter.addFooterView(llFooter);
        if (rvDoneTasks != null) {
            rvDoneTasks.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        }

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
    public void setPresenter(ListDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }

    public String getListTitle() {
        return mPresenter.getListTitle(listId);
    }

    public void setListTitle(String listTitle) {
        mPresenter.setListTitle(listId, listTitle);
    }

    public void deleteList() {
        new HintDialog.Builder(mActivity)
                .setTitle("删除这个列表")
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteList(listId);
                    }
                })
                .setNegativeButton("取消", null)
                .show(getChildFragmentManager(), "HintDialog");
    }

    @Override
    public void finish() {
        mActivity.finish();
    }

    @Override
    public void notifyDataChanged() {
        mAdapter.notifyDataSetChangedWrapper();
    }
}
