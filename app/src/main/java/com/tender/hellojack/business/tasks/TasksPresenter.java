package com.tender.hellojack.business.tasks;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.TasksRepository;
import com.tender.hellojack.model.task.TaskList;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class TasksPresenter implements TasksContract.Presenter {

    private final ResourceRepository mRepository;
    private final TasksContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    private RealmResults<TaskList> taskLists;

    public TasksPresenter(ResourceRepository mRepository, TasksContract.View mView, BaseSchedule mSchedule) {
        this.mRepository = mRepository;
        this.mView = mView;
        this.mSchedule = mSchedule;

        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!hasInit) {
            taskLists = TasksRepository.getInstance().getAllLists();
            taskLists.addChangeListener(new RealmChangeListener<RealmResults<TaskList>>() {
                @Override
                public void onChange(RealmResults<TaskList> taskLists) {
                    mView.notifyDataChanged();
                }
            });
            mView.initUIData();
            hasInit = true;
        }
    }

    @Override
    public List<TaskList> getTaskList() {
        return taskLists;
    }
}