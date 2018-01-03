package com.tender.hellojack.data.local;

import com.tender.hellojack.model.Contact;
import com.tender.hellojack.model.Friend;
import com.tender.hellojack.model.UserInfo;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by boyu on 2018/1/3.
 */

public class UserRepository {
    private static volatile UserRepository instance;

    private Realm realm;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
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

    public void addNewUser(final UserInfo newUser, final Realm.Transaction.OnSuccess onSuccess, final Realm.Transaction.OnError onError) {
        RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).equalTo("account", newUser.getAccount()).findAll();
        if (userInfos.size() > 0) {
            return;
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserInfo userInfo = realm.createObject(UserInfo.class, newUser.getAccount());
                userInfo.setName(newUser.getName());
                userInfo.setSignature(newUser.getSignature());
                userInfo.setRegion(newUser.getRegion());
                userInfo.setMobile(newUser.getMobile());
                userInfo.setGender(newUser.getGender());
                userInfo.setEmail(newUser.getEmail());
                userInfo.setBirthDay(newUser.getBirthDay());
                userInfo.setAvatar(newUser.getAvatar());
                userInfo.setAddress(newUser.getAddress());
                userInfo.setDisplayName(newUser.getDisplayName());
                userInfo.setCreateTime(new Date().getTime());
            }
        });
    }

    public RealmResults<UserInfo> getUser(final String account) {
        return realm.where(UserInfo.class).equalTo("account", account).findAll();
    }

    public void updateUserAvatar(final String account, final String avatar) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).equalTo("account", account).findAll();
                if (userInfos.size() > 0) {
                    UserInfo userInfo = userInfos.get(0);
                    userInfo.setAvatar(avatar);
                    realm.copyToRealmOrUpdate(userInfo);
                }
            }
        });
    }

    public void updateUserAddress(final String account, final String address) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).equalTo("account", account).findAll();
                if (userInfos.size() > 0) {
                    UserInfo userInfo = userInfos.get(0);
                    userInfo.setAddress(address);
                    realm.copyToRealmOrUpdate(userInfo);
                }
            }
        });
    }

    public void updateUserGender(final String account, final int gender) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).equalTo("account", account).findAll();
                if (userInfos.size() > 0) {
                    UserInfo userInfo = userInfos.get(0);
                    userInfo.setGender(gender);
                    realm.copyToRealmOrUpdate(userInfo);
                }
            }
        });
    }

    public void updateUserRegion(final String account, final String region) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).equalTo("account", account).findAll();
                if (userInfos.size() > 0) {
                    UserInfo userInfo = userInfos.get(0);
                    userInfo.setRegion(region);
                    realm.copyToRealmOrUpdate(userInfo);
                }
            }
        });
    }

    public void updateUserName(final String account, final String name) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).equalTo("account", account).findAll();
                if (userInfos.size() > 0) {
                    UserInfo userInfo = userInfos.get(0);
                    userInfo.setName(name);
                    realm.copyToRealmOrUpdate(userInfo);
                }
            }
        });
    }

    public void updateUserSignature(final String account, final String signature) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).equalTo("account", account).findAll();
                if (userInfos.size() > 0) {
                    UserInfo userInfo = userInfos.get(0);
                    userInfo.setSignature(signature);
                    realm.copyToRealmOrUpdate(userInfo);
                }
            }
        });
    }

    public void addNewFriend(final Friend friend, final Realm.Transaction.OnSuccess onSuccess, final Realm.Transaction.OnError onError) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Friend friend1 = realm.createObject(Friend.class, friend.getAccount());
                friend1.setAlias(friend.getAlias());
                friend1.setCreateTime(new Date().getTime());
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

    public void addNewContact(final Contact newContact, final Realm.Transaction.OnSuccess onSuccess, final Realm.Transaction.OnError onError) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Contact contact = realm.createObject(Contact.class, newContact.getmAccount());
                contact.setmAlias(newContact.getmAlias());
                contact.setmAvatar(newContact.getmAvatar());
                contact.setmDisplayName(newContact.getmDisplayName());
                contact.setmPinyin(newContact.getmPinyin());
                contact.setCreateTime(new Date().getTime());
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

    public RealmResults<Contact> getAllContacts() {
        return realm.where(Contact.class).findAll().sort("mPinyin");
    }
}
