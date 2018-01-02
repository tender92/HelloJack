package com.tender.hellojack.business.tasks.listdetail;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.task.Task;

import java.util.List;

/**
 * Created by boyu
 */
public class ListDetailContract {
    interface View extends IView<Presenter> {
        void finish();
        void notifyDataChanged();
    }

    interface Presenter extends IPresenter {
        List<Task> getTasksByListId(long listId);
        String getListTitle(long listId);
        void setListTitle(long listId, String listTitle);
        void deleteList(long listId);
    }
}
