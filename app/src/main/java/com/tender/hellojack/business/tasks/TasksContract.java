package com.tender.hellojack.business.tasks;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.task.TaskList;

import java.util.List;

/**
 * Created by boyu
 */
public class TasksContract {
    interface View extends IView<Presenter> {
        void notifyDataChanged();
    }

    interface Presenter extends IPresenter {
        List<TaskList> getTaskList();
    }
}
