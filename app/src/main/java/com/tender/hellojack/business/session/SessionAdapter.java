package com.tender.hellojack.business.session;

import android.content.Context;
import android.text.style.ImageSpan;
import android.view.View;

import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.emoji.MoonUtil;
import com.tender.hellojack.R;
import com.tender.hellojack.model.Message;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.model.enums.MsgTypeEnum;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.utils.TimeUtils;
import com.tender.tools.utils.ui.UIUtil;
import com.tender.tools.utils.string.StringUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by boyu on 2017/12/27.
 */

public class SessionAdapter extends LQRAdapterForRecyclerView<Message> {

    private static final int TEXT_SEND = R.layout.hj_layout_session_message_send_text;

    private Context mContext;
    private UserInfo userInfo;

    public SessionAdapter(Context context, List<Message> data, UserInfo userInfo) {
        super(context, data);
        this.mContext = context;
        this.userInfo = userInfo;
    }

    @Override
    public void convert(LQRViewHolderForRecyclerView helper, Message item, int position) {
        //设置时间
        setTime(helper, item, position);

        if (item.msgType != MsgTypeEnum.notification) {
            setHeader(helper, item);

            helper.setViewVisibility(R.id.tv_session_item_send_name, View.GONE);
        }

        if (item.msgType == MsgTypeEnum.text) {
            setTextMessage(helper, item);//文本消息
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getData().get(position);
        if (message.msgType == MsgTypeEnum.text) {
            return TEXT_SEND;
        }
        return super.getItemViewType(position);

    }

    private void setTime(LQRViewHolderForRecyclerView helper, Message item, int position) {
        if (position > 0) {
            Message preMessage = getData().get(position - 1);
            if (item.time - preMessage.time > (5 * 60 * 1000)) {//与上一条数据相关5分钟则显示时间
                helper.setViewVisibility(R.id.tv_session_item_send_time, View.VISIBLE).setText(R.id.tv_session_item_send_time, TimeUtils.getMsgFormatTime(item.time));
            } else {
                helper.setViewVisibility(R.id.tv_session_item_send_time, View.GONE);
            }
        } else {
            helper.setViewVisibility(R.id.tv_session_item_send_time, View.VISIBLE).setText(R.id.tv_session_item_send_time, TimeUtils.getMsgFormatTime(item.time));
        }
    }

    private void setHeader(LQRViewHolderForRecyclerView helper, Message item) {
        CircleImageView civHeader = helper.getView(R.id.civ_session_item_send_avatar);
        String avatar = userInfo.getAvatar();
        if (StringUtil.hasValue(avatar)) {
            ImageLoaderUtil.loadLocalImage(avatar, civHeader);
        } else {
            civHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }
    }

    private void setTextMessage(LQRViewHolderForRecyclerView helper, Message item) {
        helper.setText(R.id.tv_session_item_send_text, item.content);
        //识别并显示表情
        MoonUtil.identifyFaceExpression(UIUtil.getAppContext(), helper.getView(R.id.tv_session_item_send_text),
                item.content, ImageSpan.ALIGN_BOTTOM);
    }
}
