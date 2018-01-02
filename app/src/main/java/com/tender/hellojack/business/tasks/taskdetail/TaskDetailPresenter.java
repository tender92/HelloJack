package com.tender.hellojack.business.tasks.taskdetail;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.TasksRepository;
import com.tender.hellojack.model.task.Task;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    private final ResourceRepository mRepository;
    private final TaskDetailContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    public TaskDetailPresenter(ResourceRepository mRepository, TaskDetailContract.View mView, BaseSchedule mSchedule) {
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
    public void getTask(long taskId) {
        Task task = TasksRepository.getInstance().getTaskById(taskId).get(0);
        task.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {

            }
        });
        mView.showTask(task);
    }

    @Override
    public void updateTask(long taskId, String title, String note, boolean isDone) {
        TasksRepository.getInstance().updateTask(taskId, title, note, isDone);
        mView.finish();
    }

    @Override
    public void deleteTask(long taskId) {
        TasksRepository.getInstance().deleteTask(taskId, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mView.finish();
            }
        }, null);
    }
}