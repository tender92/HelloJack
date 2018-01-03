package com.tender.hellojack.business.session;

import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.UserRepository;
import com.tender.hellojack.model.Message;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.model.enums.MsgDirectionEnum;
import com.tender.hellojack.model.enums.MsgTypeEnum;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.TimeUtils;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.utils.string.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class SessionPresenter implements SessionContract.Presenter {

    private final ResourceRepository mRepository;
    private final SessionContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    private List<Message> messages = new ArrayList<>();

    public SessionPresenter(ResourceRepository mRepository, SessionContract.View mView, BaseSchedule mSchedule) {
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
    public UserInfo getUserInfo(String account) {
        UserInfo userInfo = UserRepository.getInstance().getUser(account).get(0);
        userInfo.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                mView.notifyDataChanged();
            }
        });
        return userInfo;
    }

    @Override
    public List<Message> loadHistoryData() {
        return messages;
    }

    @Override
    public void sendMessage(String content) {
        Message message = new Message();
        message.msgType = MsgTypeEnum.text;
        message.msgDirection = MsgDirectionEnum.Out;
        message.time = TimeUtils.getNowTimeMills();
        message.uuid = UUIDGenerator.generate();
        message.content = content;
        messages.add(message);
        mView.showSendMsg(message);
    }

    @Override
    public void sendCustomMessage(String content, MsgAttachment attachment) {
        Message message = new Message();
        message.msgType = MsgTypeEnum.custom;
        message.msgDirection = MsgDirectionEnum.Out;
        message.time = TimeUtils.getNowTimeMills();
        message.uuid = UUIDGenerator.generate();
        message.content = content;
        message.attachment = attachment;
        messages.add(message);
        mView.showSendMsg(message);
    }
}