package com.tender.hellojack.business.tasks.addtasktolist;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;

/**
 * Created by boyu
 */
public class AddTaskToListContract {
    interface View extends IView<Presenter> {
        void showSelectedList(String title);
        void showAddNewListHint();
        void showSelectListDialog();
        void showAddNewListDialog();
        void finishActivity();
    }

    interface Presenter extends IPresenter {
        void selectTaskList();
        void OnTaskListSelected(long listId, String title);
        void addTaskList(String taskTitle);
    }
}
