package com.tender.hellojack.model;

import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.tender.hellojack.model.enums.MsgDirectionEnum;
import com.tender.hellojack.model.enums.MsgTypeEnum;

import java.io.Serializable;

/**
 * Created by boyu on 2017/12/27.
 */

public class Message implements Serializable {
    public String uuid;//消息uuid
    public MsgTypeEnum msgType;//消息类型
    public MsgDirectionEnum msgDirection;//消息方向
    public String content;//消息内容
    public long time;//时间
    public MsgAttachment attachment;//消息附件对象
}
