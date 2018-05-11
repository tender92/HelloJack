package com.tender.hellojack.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.tender.speech.SpeechConfig;
import com.tender.speech.SpeechManager;
import com.tender.tools.TenderLog;
import com.tender.tools.utils.ui.UIUtil;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by boyu on 17/5/9.
 */
public class SpeechService extends Service {
    private SpeechManager speechManager = SpeechManager.getInstance(UIUtil.getAppContext());

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // 此处需要将setApiKey方法的两个参数替换为你在百度开发者中心注册应用所得到的apiKey和secretKey
        ApplicationInfo appInfo = this.getPackageManager()
                .getApplicationInfo(getPackageName(),
                        PackageManager.GET_META_DATA);
            String appId =appInfo.metaData.getString("com.baidu.apiID");
            String appKey = appInfo.metaData.getString("com.baidu.apiKey");
            String appSecretKey = appInfo.metaData.getString("com.baidu.secretKey");
            SpeechConfig config = new SpeechConfig(appId, appKey, appSecretKey, TtsMode.MIX, "F", getParams(), new MySynthesizerListener());
            speechManager.initSpeech(config);
        } catch (PackageManager.NameNotFoundException e) {
            TenderLog.e(e.getMessage());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String words = intent.getStringExtra("words");
        speechManager.speak(words);

        return START_STICKY;
    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "5"); // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");// 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);         // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        return params;
    }


    /**
     * 语音合成监听器
     */
    private class MySynthesizerListener implements SpeechSynthesizerListener {

        @Override
        public void onSynthesizeStart(String utteranceId) {
            TenderLog.d("准备开始合成,序列号:" + utteranceId);
        }

        @Override
        public void onSynthesizeDataArrived(String utteranceId, byte[] bytes, int progress) {
            TenderLog.d("合成进度回调, progress：" + progress + ";序列号:" + utteranceId);
        }

        @Override
        public void onSynthesizeFinish(String utteranceId) {
            TenderLog.d("合成结束回调, 序列号:" + utteranceId);
        }

        @Override
        public void onSpeechStart(String utteranceId) {
            TenderLog.d("播放开始回调, 序列号:" + utteranceId);
        }

        @Override
        public void onSpeechProgressChanged(String utteranceId, int progress) {
            TenderLog.d("播放进度回调, progress：" + progress + ";序列号:" + utteranceId);
        }

        @Override
        public void onSpeechFinish(String utteranceId) {
            TenderLog.d("播放结束回调, 序列号:" + utteranceId);
        }

        @Override
        public void onError(String utteranceId, SpeechError speechError) {
            TenderLog.d("错误发生：" + speechError.description + "，错误编码："
                    + speechError.code + "，序列号:" + utteranceId);
        }
    }
}
