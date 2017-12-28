package com.tender.hellojack.business.session;

import com.tender.hellojack.base.IPresenter;
import com.tender.hellojack.base.IView;
import com.tender.hellojack.model.Message;

import java.util.List;

/**
 * Created by boyu
 */
public class SessionContract {
    interface View extends IView<Presenter> {
        void showAudioBtnVisibility();
        boolean showBottomVisibility();
        void showBtnAudio();
        void hideBtnAudio();
        void showPlayAudio();
        void hidePlayAudio();
        void updateChronometerTip(boolean cancel);
        void openKeyBoardAndGetFocus();
        void showSendMsg(Message message);
    }

    interface Presenter extends IPresenter {
        List<Message> loadHistoryData();
        void sendMessage(String content);
    }
}
