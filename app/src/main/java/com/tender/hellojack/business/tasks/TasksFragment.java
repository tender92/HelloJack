package com.tender.hellojack.business.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.optionitemview.OptionItemView;
import com.lqr.recyclerview.LQRRecyclerView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.tasks.addtasktolist.AddTaskToListActivity;
import com.tender.hellojack.business.tasks.listdetail.ListDetailActivity;
import com.tender.hellojack.model.task.TaskList;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.ui.DisplayUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class TasksFragment extends BaseFragment implements TasksContract.View {

    private TasksContract.Presenter mPresenter;

    private LQRRecyclerView rvTasks;
    private com.ddz.floatingactionbutton.FloatingActionButton fabAddList;
    private TextView tvHeader;
    private LinearLayout llFooter;

    private LQRAdapterForRecyclerView<TaskList> mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_tasks, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        rvTasks = (LQRRecyclerView) root.findViewById(R.id.cv_tasks_list);
        fabAddList = (com.ddz.floatingactionbutton.FloatingActionButton) root.findViewById(R.id.fab_tasks_add_list);

        RxView.clicks(fabAddList).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, AddTaskToListActivity.class));
            }
        });

        tvHeader = new TextView(mActivity);
        tvHeader.setText("主页列表");
        tvHeader.setTextSize(20);
        tvHeader.setPadding(DisplayUtil.sp2px(mActivity, 15), 0, 0, 0);
        ViewGroup.LayoutParams hParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mActivity, 48));
        tvHeader.setGravity(Gravity.CENTER_VERTICAL);
        tvHeader.setLayoutParams(hParams);

        llFooter = (LinearLayout) ViewGroup.inflate(mActivity, R.layout.hj_layout_tasks_list_footer, null);
        RxView.clicks(llFooter).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                AddTaskListDialog dialog = new AddTaskListDialog(1);
                dialog.show(getChildFragmentManager(), "AddTaskListDialog");
            }
        });

        return root;
    }

    @Override
    public void initUIData() {
        setAdapter();
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
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void setAdapter() {
        List<TaskList> taskLists = mPresenter.getTaskList();
        mAdapter = new LQRAdapterForRecyclerView<TaskList>(mActivity, R.layout.hj_layout_tasks_item, taskLists) {
            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final TaskList item, int position) {
                ((OptionItemView)helper.getView(R.id.oiv_tasks_item)).setLeftText(item.getTitle());
                ((OptionItemView)helper.getView(R.id.oiv_tasks_item)).setRightText(String.valueOf(item.getTasks().size()));

                RxView.clicks(helper.getView(R.id.oiv_tasks_item)).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(mActivity, ListDetailActivity.class);
                        intent.putExtra(IntentConst.IRParam.List_ITEM_ID, item.getId());
                        intent.putExtra(IntentConst.IRParam.List_ITEM_TITLE, item.getTitle());
                        startActivity(intent);
                    }
                });
            }
        };
        mAdapter.addHeaderView(tvHeader);
        mAdapter.addFooterView(llFooter);
        if (rvTasks != null) {
            rvTasks.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        }
    }

    @Override
    public void notifyDataChanged() {
        mAdapter.notifyDataSetChangedWrapper();
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

            mTitle.setText("任务列表");
        }
    }
}
