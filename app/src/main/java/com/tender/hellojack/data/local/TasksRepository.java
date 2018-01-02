package com.tender.hellojack.data.local;

import com.tender.hellojack.model.task.Task;
import com.tender.hellojack.model.task.TaskList;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by boyu on 2017/12/29.
 */

public class TasksRepository {
    private static volatile TasksRepository instance;

    private Realm realm;

    private TasksRepository() {

    }

    public static TasksRepository getInstance() {
        if (instance == null) {
            synchronized (TasksRepository.class) {
                if (instance == null) {
                    instance = new TasksRepository();
                }
            }
        }
        return instance;
    }

    public void initRealm() {
        realm = Realm.getDefaultInstance();
    }

    public void close() {
        realm.close();
    }

    public RealmResults<TaskList> getAllLists() {
        return realm.where(TaskList.class).findAll().sort("createTime", Sort.ASCENDING);
    }

    public RealmResults<TaskList> getListById(long listId) {
        return realm.where((TaskList.class)).equalTo("id", listId).findAll();
    }

    public void updateListTitle(final long listId, final String title) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TaskList> taskLists = realm.where(TaskList.class).equalTo("id", listId).findAll();
                if (taskLists.size() > 0) {
                    TaskList taskList = taskLists.get(0);
                    taskList.setTitle(title);
                    realm.copyToRealmOrUpdate(taskList);
                }
            }
        });
    }

    public void addNewList(final String title, final Realm.Transaction.OnSuccess onSuccess, final Realm.Transaction.OnError onError) {
        Number maxIdNumber = realm.where(TaskList.class).max("id");
        final long nextId = maxIdNumber != null ? maxIdNumber.longValue() + 1 : 1;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TaskList taskList = realm.createObject(TaskList.class, nextId);
                taskList.setTitle(title);
                taskList.setCreateTime(new Date().getTime());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onSuccess != null) {
                    onSuccess.onSuccess();
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (onError != null) {
                    onError.onError(error);
                }
            }
        });
    }

    public void deleteList(final long listId, final Realm.Transaction.OnSuccess onSuccess, final Realm.Transaction.OnError onError) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TaskList> taskLists = realm.where(TaskList.class).equalTo("id", listId).findAll();
                if (taskLists.size() > 0) {
                    TaskList taskList = taskLists.get(0);
                    taskList.getTasks().deleteAllFromRealm();
                    taskList.deleteFromRealm();
                }
            }
        }, onSuccess, onError);
    }

    public void addNewTaskToList(final long listId, final String taskTitle, final Realm.Transaction.OnSuccess onSuccess,
                                 final Realm.Transaction.OnError onError) {
        Number maxIdNumber = realm.where(Task.class).max("id");
        final long nextId = maxIdNumber != null ? maxIdNumber.longValue() + 1 : 1;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TaskList> taskLists = realm.where(TaskList.class).equalTo("id", listId).findAll();
                if (taskLists.size() > 0) {
                    TaskList taskList = taskLists.get(0);
                    Task task = new Task();
                    task.setId(nextId);
                    task.setTitle(taskTitle);
                    task.setListId(listId);
                    task.setCreateTime(new Date().getTime());
                    taskList.addTask(task);
                    realm.copyToRealmOrUpdate(task);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onSuccess != null) {
                    onSuccess.onSuccess();
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (onError != null) {
                    onError.onError(error);
                }
            }
        });
    }

    public RealmResults<Task> getGroupTasksById(long listId) {
        return realm.where(Task.class).equalTo("listId", listId)
                .findAll()
                .sort("createTime", Sort.ASCENDING);
    }

    public RealmResults<Task> getTaskById(long taskId) {
       return realm.where(Task.class).equalTo("id", taskId).findAll();
    }

    public void updateTask(final long taskId, final String title, final String note, final boolean isDone) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Task> results = realm.where(Task.class).equalTo("id", taskId).findAll();
                if (results.size() > 0) {
                    Task task = results.get(0);
                    task.setTitle(title);
                    task.setNote(note);
                    task.setDone(isDone);
                    realm.copyToRealmOrUpdate(task);
                }
            }
        });
    }

    public void deleteTask(final long taskId, final Realm.Transaction.OnSuccess onSuccess, final Realm.Transaction.OnError onError) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Task> tasks = realm.where(Task.class).equalTo("id", taskId).findAll();
                tasks.deleteAllFromRealm();
            }
        }, onSuccess, onError);
    }
}
