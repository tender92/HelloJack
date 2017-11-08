package com.tender.speech;

import android.content.Context;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.tender.tools.TenderLog;

import java.util.Map;

/**
 * Created by boyu on 2017/11/7.
 */

public class MySynthesizer {

    private SpeechSynthesizer mSpeechSynthesizer;

    private Context context;

    private static boolean isInitied = false;

    public MySynthesizer(Context context, SpeechConfig config) {
        this.context = context;
        init(config);
    }

    private void init(SpeechConfig config) {
        TenderLog.d("百度语音合成初始化开始");

        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setSpeechSynthesizerListener(config.getListener());

        // 请替换为语音开发者平台上注册应用得到的App ID ,AppKey ，Secret Key
        mSpeechSynthesizer.setAppId(config.getAppId());
        mSpeechSynthesizer.setApiKey(config.getAppKey(), config.getSecretKey());

        boolean isMix = config.getTtsMode().equals(TtsMode.MIX);
        if (isMix) {
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, FilePath.TEXT_MODEL_NAME); // 文本模型文件路径 (离线引擎使用)
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, FilePath.SPEECH_FEMALE_MODEL_NAME);  // 声学模型文件路径 (离线引擎使用)

            // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。选择纯在线可以不必调用auth方法。
            AuthInfo authInfo = mSpeechSynthesizer.auth(config.getTtsMode());
            if (!authInfo.isSuccess()) {
                String errorMsg = authInfo.getTtsError().getDetailMessage();
                TenderLog.d("百度语音合成授权失败：" + errorMsg);
            } else {
                TenderLog.d("百度语音合成授权成功！");
            }
        }
        setParams(config.getParams());
        int result = mSpeechSynthesizer.initTts(config.getTtsMode());
        if (result != 0) {
            TenderLog.d("【error】initTts 初始化失败 + errorCode：" + result);
        } else {
            TenderLog.d("合成引擎初始化成功!");
        }
    }

    /**
     * 合成并播放
     * @param text 小于1024 GBK字节，即512个汉字或者字母数字
     * @return
     */
    public int speak(String text) {
        return mSpeechSynthesizer.speak(text);
    }

    /**
     * 合成并播放
     * @param text 小于1024 GBK字节，即512个汉字或者字母数字
     * @param utteranceId 用于listener的回调，默认"0"
     * @return
     */
    public int speak(String text, String utteranceId) {
        return mSpeechSynthesizer.speak(text, utteranceId);
    }

    public int pause() {
        return mSpeechSynthesizer.pause();
    }

    public int resume() {
        return mSpeechSynthesizer.resume();
    }

    public int stop() {
        return mSpeechSynthesizer.stop();
    }

    /**
     * 设置播放音量，默认已经是最大声音
     * 0.0f为最小音量，1.0f为最大音量
     *
     * @param leftVolume  [0-1] 默认1.0f
     * @param rightVolume [0-1] 默认1.0f
     */
    public void setStereoVolume(float leftVolume, float rightVolume) {
        mSpeechSynthesizer.setStereoVolume(leftVolume, rightVolume);
    }

    /**
     * 引擎在合成时该方法不能调用！！！
     * 注意 只有 TtsMode.MIX 才可以切换离线发音
     *
     * @param voiceType "F" 或者 "M"
     * @return
     */
    public int loadModel(String voiceType) {
        int res = -9999;
        String speechModePath = "";
        if (voiceType.equals("F")) {
            speechModePath = FilePath.SPEECH_FEMALE_MODEL_NAME;
        } else if (voiceType.equals("M")) {
            speechModePath = FilePath.SPEECH_MALE_MODEL_NAME;
        }
        res = mSpeechSynthesizer.loadModel(speechModePath, FilePath.TEXT_MODEL_NAME);
        String voiceStr = voiceType.equals("F") ? "离线女声" : "离线男声";
        TenderLog.d("切换离线发音人成功。当前发音人：" + voiceStr);
        return res;
    }

    /**
     * 释放引擎
     */
    public void release() {
        mSpeechSynthesizer.stop();
        mSpeechSynthesizer.release();
        mSpeechSynthesizer = null;
        isInitied = false;
    }

    /**
     * 设置引擎参数
     * @param params
     */
    private void setParams(Map<String, String> params) {
        if (params != null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                mSpeechSynthesizer.setParam(e.getKey(), e.getValue());
            }
        }
    }
}
