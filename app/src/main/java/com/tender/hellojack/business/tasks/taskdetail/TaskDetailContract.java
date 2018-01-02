package com.tender.hellojack.business.tasks.taskdetail;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.task.Task;

/**
 * Created by boyu
 */
public class TaskDetailContract {
    interface View extends IView<Presenter> {
        void showTask(Task task);
        void finish();
    }

    interface Presenter extends IPresenter {
        void getTask(long taskId);
        void updateTask(long taskId, String title, String note, boolean isDone);
        void deleteTask(long taskId);
    }
}
