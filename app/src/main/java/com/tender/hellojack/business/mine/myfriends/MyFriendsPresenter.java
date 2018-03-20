package com.tender.hellojack.business.mine.myfriends;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.contact.Contact;
import com.tender.hellojack.model.contact.Friend;
import com.tender.hellojack.model.contact.UserInfo;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class MyFriendsPresenter implements MyFriendsContract.Presenter {

    private final ResourceRepository mRepository;
    private final MyFriendsContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    public MyFriendsPresenter(ResourceRepository mRepository, MyFriendsContract.View mView, BaseSchedule mSchedule) {
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
    public void initContacts() {
        List<Friend> friends = new ArrayList<>();
        Friend friend = new Friend();
        friend.setAccount("xiaoming1");
        friend.setAlias("朱长江");
        friends.add(friend);

        friend = new Friend();
        friend.setAccount("xiaoming2");
        friend.setAlias("沈亦臻");
        friends.add(friend);

        friend = new Friend();
        friend.setAccount("xiaoming3");
        friend.setAlias("崔皓月");
        friends.add(friend);

        friend = new Friend();
        friend.setAccount("xiaoming4");
        friend.setAlias("莫晓俊");
        friends.add(friend);

        friend = new Friend();
        friend.setAccount("xiaoming5");
        friend.setAlias("莫晓娜");
        friends.add(friend);

        friend = new Friend();
        friend.setAccount("xiaoming6");
        friend.setAlias("星星");
        friends.add(friend);

        friend = new Friend();
        friend.setAccount("xiaoming7");
        friend.setAlias("X先生");
        friends.add(friend);

        for (int i = 0; i < friends.size(); i ++) {
            UserRepository.getInstance().addNewFriend(friends.get(i), null, null);//创建朋友

            UserInfo userInfo = new UserInfo();//创建用户
            userInfo.setAccount(friends.get(i).getAccount());
            userInfo.setDisplayName("大锤子哥");
            userInfo.setAddress("上海市浦东新区张衡路");
            userInfo.setAvatar("");
            userInfo.setBirthDay("1996-03-08");
            userInfo.setEmail("hdlgdx987@163.com");
            userInfo.setGender(1);
            userInfo.setMobile("13888888888");
            userInfo.setName("小明");
            userInfo.setRegion("上海市");
            userInfo.setSignature("小明不迟到");
            UserRepository.getInstance().addNewUser(userInfo, null, null);

            Contact contact = new Contact(friends.get(i), userInfo);
            UserRepository.getInstance().addNewContact(contact, null, null);
        }
    }

    @Override
    public List<Contact> getSortedContacts() {
        RealmResults<Contact> contacts = UserRepository.getInstance().getAllContacts();
        contacts.addChangeListener(new RealmChangeListener<RealmResults<Contact>>() {
            @Override
            public void onChange(RealmResults<Contact> contacts) {
                sortContacts(contacts);
                mView.notifyDataChanged();
            }
        });
        sortContacts(contacts);
        return contacts;
    }

    @Override
    public void sortContacts(List<Contact> contacts) {
//        Collections.sort(contacts);// 排序后由于#号排在上面，故得把#号的部分集合移到集合的最下面

        List<Contact> specialList = new ArrayList<Contact>();

        for (int i = 0; i < contacts.size(); i++) {
            // 将属于#号的集合分离开来
            if (contacts.get(i).getmPinyin().equals("#")) {
                specialList.add(contacts.get(i));
            }
        }

        if (specialList.size() != 0) {
            contacts.removeAll(specialList);// 先移出掉顶部的#号部分
            contacts.addAll(contacts.size(), specialList);// 将#号的集合添加到集合底部
        }
    }
}