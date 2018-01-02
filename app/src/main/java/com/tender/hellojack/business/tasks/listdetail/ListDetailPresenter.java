package com.tender.hellojack.business.tasks.listdetail;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.TasksRepository;
import com.tender.hellojack.model.task.Task;
import com.tender.hellojack.model.task.TaskList;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class ListDetailPresenter implements ListDetailContract.Presenter {

    private final ResourceRepository mRepository;
    private final ListDetailContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    public ListDetailPresenter(ResourceRepository mRepository, ListDetailContract.View mView, BaseSchedule mSchedule) {
        this.mRepository = mRepository;
        this.mView = mView;
        this.mSchedule = mSchedule;

        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!hasInit) {
            mView.initUIData();
            hasInit = true;
        }
    }

    @Override
    public List<Task> getTasksByListId(final long listId) {
        RealmResults<Task> tasks = TasksRepository.getInstance().getGroupTasksById(listId);
        tasks.addChangeListener(new RealmChangeListener<RealmResults<Task>>() {
            @Override
            public void onChange(RealmResults<Task> tasks) {
                mView.notifyDataChanged();
            }
        });
        return tasks;
    }

    @Override
    public String getListTitle(long listId) {
        return TasksRepository.getInstance().getListById(listId).get(0).getTitle();
    }

    @Override
    public void setListTitle(long lisId, String listTitle) {
        if (!listTitle.equalsIgnoreCase(getListTitle(lisId))) {
            TasksRepository.getInstance().updateListTitle(lisId, listTitle);
        }
    }

    @Override
    public void deleteList(long listId) {
        TasksRepository.getInstance().deleteList(listId, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mView.finish();
            }
        }, null);
    }
}