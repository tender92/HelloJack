package com.tender.hellojack.model.enums;

/**
 * Created by boyu on 2017/12/27.
 */

public enum MsgTypeEnum {
    /**
     * 未知消息类型
     */
    undef(-1, "Unknown"),

    /**
     * 文本消息类型
     */
    text(0, ""),

    /**
     * 图片消息
     */
    image(1, "图片"),

    /**
     * 音频消息
     */
    audio(2, "语音"),

    /**
     * 视频消息
     */
    video(3, "视频"),

    /**
     * 位置消息
     */
    location(4, "位置"),

    /**
     * 文件消息
     */
    file(5, "文件"),

    /**
     * 音视频通话
     */
    avchat(6, "音视频通话"),

    /**
     * 通知消息
     */
    notification(7, "通知消息"),

    /**
     * 提醒类型消息
     */
    tip(8, "提醒消息"),

    /**
     * 第三方APP自定义消息
     */
    custom(9, "自定义消息");

    final private int value;
    final private String sendMessageTip;

    MsgTypeEnum(int value, String sendMessageTip) {
        this.value = value;
        this.sendMessageTip = sendMessageTip;
    }

    public final int getValue() {
        return value;
    }

    public final String getSendMessageTip() {
        return sendMessageTip;
    }
}
