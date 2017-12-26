package com.tender.hellojack.business.mine.myfriends;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.Contact;
import com.tender.hellojack.model.Friend;
import com.tender.hellojack.model.UserInfo;

import java.util.List;

/**
 * Created by boyu
 */
public class MyFriendsContract {
    interface View extends IView<Presenter> {
        void showSelectLetter(String letter);
        void showHeaderViewUnread();
    }

    interface Presenter extends IPresenter {
        List<Friend> getFriends();
        UserInfo getUserInfo(String account);
        void sortContacts(List<Contact> contacts);
    }
}
