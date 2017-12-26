package com.tender.hellojack.business.mine.myfriends;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.model.Contact;
import com.tender.hellojack.model.Friend;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.model.enums.GenderEnum;
import com.tender.tools.manager.PrefManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public List<Friend> getFriends() {
        List<Friend> friends = new ArrayList<>();
        Friend friend = new Friend();
        friend.account = "xiaoming1";
        friend.alias = "朱长江";
        friends.add(friend);

        friend = new Friend();
        friend.account = "xiaoming2";
        friend.alias = "沈亦臻";
        friends.add(friend);

        friend = new Friend();
        friend.account = "xiaoming3";
        friend.alias = "崔皓月";
        friends.add(friend);

        friend = new Friend();
        friend.account = "xiaoming3";
        friend.alias = "莫晓俊";
        friends.add(friend);

        friend = new Friend();
        friend.account = "xiaoming3";
        friend.alias = "莫晓娜";
        friends.add(friend);

        friend = new Friend();
        friend.account = "xiaoming3";
        friend.alias = "星星";
        friends.add(friend);

        friend = new Friend();
        friend.account = "xiaoming3";
        friend.alias = "X先生";
        friends.add(friend);

        return friends;
    }

    @Override
    public UserInfo getUserInfo(String account) {
        UserInfo userInfo = new UserInfo();
        userInfo.account = "xiaoming";
        userInfo.address = "上海市浦东新区张衡路";
        userInfo.avatar = PrefManager.getUserHeaderPath();
        userInfo.birthDay = "1996-03-08";
        userInfo.email = "hdlgdx987@163.com";
        userInfo.gender = GenderEnum.MALE;
        userInfo.mobile = "13888888888";
        userInfo.name = "小明";
        userInfo.region = "上海市";
        userInfo.signatur = "小明不迟到";
        return userInfo;
    }

    @Override
    public void sortContacts(List<Contact> contacts) {
        Collections.sort(contacts);// 排序后由于#号排在上面，故得把#号的部分集合移到集合的最下面

        List<Contact> specialList = new ArrayList<Contact>();

        for (int i = 0; i < contacts.size(); i++) {
            // 将属于#号的集合分离开来
            if (contacts.get(i).mPinyin.equals("#")) {
                specialList.add(contacts.get(i));
            }
        }

        if (specialList.size() != 0) {
            contacts.removeAll(specialList);// 先移出掉顶部的#号部分
            contacts.addAll(contacts.size(), specialList);// 将#号的集合添加到集合底部
        }
    }
}