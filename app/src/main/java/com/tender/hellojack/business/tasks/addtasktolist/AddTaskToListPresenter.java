package com.tender.hellojack.business.tasks.addtasktolist;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.TasksRepository;
import com.tender.hellojack.model.task.TaskList;
import com.tender.tools.utils.string.StringUtil;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class AddTaskToListPresenter implements AddTaskToListContract.Presenter {

    private final ResourceRepository mRepository;
    private final AddTaskToListContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private TasksRepository tasksRepository;
    private RealmResults<TaskList> taskLists;
    private static final int INVALID_LIST_ID = -1;
    private long selectedListId = INVALID_LIST_ID;

    private boolean hasInit = false;

    public AddTaskToListPresenter(ResourceRepository mRepository, AddTaskToListContract.View mView, BaseSchedule mSchedule) {
        this.mRepository = mRepository;
        this.mView = mView;
        this.mSchedule = mSchedule;

        mSubscription = new CompositeSubscription();
        tasksRepository = TasksRepository.getInstance();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!hasInit) {
            mView.initUIData();
            taskLists = tasksRepository.getAllLists();
            useFirstListAsDefault();
            taskLists.addChangeListener(new RealmChangeListener<RealmResults<TaskList>>() {
                @Override
                public void onChange(RealmResults<TaskList> taskLists) {
                    useFirstListAsDefault();
                }
            });
            hasInit = true;
        }
    }

    private void useFirstListAsDefault() {
        if (taskLists.size() > 0) {
            TaskList taskList = taskLists.get(0);
            selectedListId = taskList.getId();
            mView.showSelectedList(taskList.getTitle());
        } else {
            mView.showAddNewListHint();
        }
    }

    @Override
    public void selectTaskList() {
        if (taskLists.size() > 0) {
            mView.showSelectListDialog();
        } else {
            mView.showAddNewListDialog();
        }
    }

    @Override
    public void OnTaskListSelected(long listId, String title) {
        selectedListId = listId;
        mView.showSelectedList(title);
    }

    @Override
    public void addTaskList(String taskTitle) {
        if (selectedListId != INVALID_LIST_ID && StringUtil.hasValue(taskTitle)) {
            TasksRepository.getInstance().addNewTaskToList(selectedListId, taskTitle, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    mView.finishActivity();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {

                }
            });
        }
    }
}