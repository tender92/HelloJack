package com.tender.speech;

import android.os.Environment;

/**
 * Created by boyu on 2017/11/7.
 */

public class FilePath {
    public static final String SD_CARD_DIR = Environment.getExternalStorageDirectory().toString();
    public static final String SPEECH_DIR_NAME = SD_CARD_DIR + "/baiduTTS";
    public static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    public static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    public static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
}
