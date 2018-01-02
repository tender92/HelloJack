package com.tender.hellojack.business.tasks.addtasktolist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.optionitemview.OptionItemView;
import com.lqr.recyclerview.LQRRecyclerView;
import com.tender.hellojack.R;
import com.tender.hellojack.data.local.TasksRepository;
import com.tender.hellojack.model.task.TaskList;
import com.tender.hellojack.utils.ScheduleProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by boyu on 2017/12/29.
 */

public class SelectListDialog extends DialogFragment {

    private Context context;
    private CallBack callBack;

    private LQRRecyclerView rvList;
    private LQRAdapterForRecyclerView mAdapter;

    public interface CallBack {
        void onSelectList(long listId, String title);
    }

    public SelectListDialog() {}

    public void setArguments(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_layout_tasks_list, container, false);
        rvList = (LQRRecyclerView) root.findViewById(R.id.rv_add_task_to_list);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        List<TaskList> taskLists = TasksRepository.getInstance().getAllLists();
        mAdapter = new LQRAdapterForRecyclerView<TaskList>(context, R.layout.hj_layout_tasks_item, taskLists) {

            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final TaskList item, int position) {
                ((OptionItemView)helper.getView(R.id.oiv_tasks_item)).setLeftText(item.getTitle());

                RxView.clicks(helper.getView(R.id.oiv_tasks_item)).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        dismiss();
                        if (callBack != null) {
                            callBack.onSelectList(item.getId(), item.getTitle());
                        }
                    }
                });
            }
        };
        rvList.setAdapter(mAdapter);
    }

}
