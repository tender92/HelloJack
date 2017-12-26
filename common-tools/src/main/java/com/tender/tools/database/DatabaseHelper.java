package com.tender.tools.database;

import com.tender.tools.utils.string.StringUtil;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by boyu on 2017/12/26.
 */

public class DatabaseHelper {

    private DatabaseHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    private static DatabaseHelper instance;

    private static Realm mRealm;

    /**
     * @return
     */
    synchronized public static DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper();
        }

        return instance;
    }

    /**
     * 插入数据
     * 对象存在修改该对象，不存在，创建一个
     *
     * @param a
     */
    public boolean copyToRealmOrUpdate(final RealmObject a) {
        if (a != null) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(a);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * 根据ID查询数据
     *
     * @param clazz
     * @param id
     * @param <E>
     * @return
     */
    public <E extends RealmObject> E queryInfoById(Class<E> clazz, String id) {
        return mRealm.where(clazz).equalTo("id", id).findFirst();
    }

    /**
     * 返回第一条数据
     * @param eClass
     * @param <E>
     * @return
     */
    public <E extends RealmObject> E queryObject(Class<E> eClass) {
        return mRealm.where(eClass).findFirst();
    }

    /**
     * 查询所有数据
     *
     * @param clazz
     * @param <E>
     * @return
     */
    public <E extends RealmObject> List<E> queryAll(Class<E> clazz) {

        RealmResults<E> lists = mRealm.where(clazz).findAll();
        //降序
        lists = lists.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(lists);
    }

    /**
     * 将json
     *
     * @param clazz
     * @param json
     * @param <E>
     * @return
     */
    public <E extends RealmObject> boolean insertObjectByJson(final Class<E> clazz, final String json) {
        if (StringUtil.hasValue(json)) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateObjectFromJson(clazz, json);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * 删除数据
     *
     * @param eClass
     * @param <E>
     * @return
     */
    public <E extends RealmObject> boolean deleteObject(final Class<E> eClass) {
        final RealmResults<E> lists = mRealm.where(eClass).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                lists.deleteAllFromRealm();//删除所有数据
            }
        });

        return true;
    }

    /**
     * 根据ID删除数据
     *
     * @param eClass
     * @param id
     * @param <E>
     * @return
     */
    public <E extends RealmObject> boolean deleteObjectById(final Class<E> eClass, String id) {
        final RealmObject realObject = mRealm.where(eClass).equalTo("id", id).findFirst();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realObject.deleteFromRealm();
            }
        });
        return true;
    }

    /**
     * 新线程添加数据
     *
     * @param a
     */
    public void insertDataAnsyc(final RealmObject a) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(a);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    /**
     * 关闭
     */
    public void close() {
        if (mRealm != null) {
            mRealm.close();
        }
    }
}
