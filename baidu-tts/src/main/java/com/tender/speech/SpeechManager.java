package com.tender.speech;

import android.content.Context;
import android.content.res.AssetManager;

import com.tender.tools.utils.file.FileUtil;

import java.io.File;

/**
 * Created by boyu on 2017/11/7.
 */

public class SpeechManager {

    private static SpeechManager instance;
    private Context context;
    public MySynthesizer synthesizer;

    private SpeechManager(Context context) {
        this.context = context;
        copyAssets();
    }

    synchronized public static SpeechManager getInstance(Context context) {
        if (instance == null) {
            instance = new SpeechManager(context);
        }
        return instance;
    }

    /**
     *  把assets资源文件复制到sd卡
     */
    private void copyAssets() {
        AssetManager asset = context.getAssets();
        File file = new File(FilePath.SPEECH_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileUtil.copyFromAssets(asset, FilePath.TEXT_MODEL_NAME,
                FilePath.SPEECH_DIR_NAME + "/" + FilePath.TEXT_MODEL_NAME, false);
        FileUtil.copyFromAssets(asset, FilePath.SPEECH_FEMALE_MODEL_NAME,
                FilePath.SPEECH_DIR_NAME + "/" + FilePath.SPEECH_FEMALE_MODEL_NAME, false);
        FileUtil.copyFromAssets(asset, FilePath.SPEECH_MALE_MODEL_NAME,
                FilePath.SPEECH_DIR_NAME + "/" + FilePath.SPEECH_MALE_MODEL_NAME, false);
    }

    public void initSpeech(SpeechConfig config) {
        synthesizer = new MySynthesizer(context, config);
    }

    public int speak(String text) {
        return synthesizer.speak(text);
    }

    public int speak(String text, String utteranceId) {
        return synthesizer.speak(text, utteranceId);
    }

    public int pause() {
        return synthesizer.pause();
    }

    public int resume() {
        return synthesizer.resume();
    }

    public int stop() {
        return synthesizer.stop();
    }

    public void setStereoVolume(float leftVolume, float rightVolume) {
        synthesizer.setStereoVolume(leftVolume, rightVolume);
    }

    public int loadModel(String voiceType) {
        return synthesizer.loadModel(voiceType);
    }

    public void release() {
        synthesizer.release();
    }
}
