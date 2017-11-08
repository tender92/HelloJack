package com.tender.speech;

import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.util.Map;

/**
 * Created by boyu on 2017/11/7.
 */

public class SpeechConfig {

    private String appId;

    private String appKey;

    private String secretKey;

    /**
     * 纯在线或者离在线融合
     */
    private TtsMode ttsMode;

    /**
     * 离线发音，离线男声或者女声
     */
    private String offlineVoice;

    /**
     * 初始化的其它参数，用于setParam
     */
    private Map<String, String> params;

    /**
     * 合成引擎的回调
     */
    private SpeechSynthesizerListener listener;

    public SpeechConfig(String appId, String appKey, String secretKey,
                        TtsMode ttsMode, String offlineVoice,
                        Map<String, String> params,
                        SpeechSynthesizerListener listener) {
        this.appId = appId;
        this.appKey = appKey;
        this.secretKey = secretKey;
        this.ttsMode = ttsMode;
        this.offlineVoice = offlineVoice;
        this.params = params;
        this.listener = listener;
    }

    public SpeechSynthesizerListener getListener() {
        return listener;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getOfflineVoice() {
        return offlineVoice;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public TtsMode getTtsMode() {
        return ttsMode;
    }
}
