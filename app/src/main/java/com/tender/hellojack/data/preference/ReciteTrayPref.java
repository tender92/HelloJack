package com.tender.hellojack.data.preference;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tender.hellojack.model.translate.EDurationTipTime;
import com.tender.hellojack.model.translate.EIntervalTipTime;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Created by boyu on 2018/2/1.
 */

public class ReciteTrayPref extends TrayPreferences {

    private static final boolean DEFAULT_IS_RECITE = false;
    //is open JIT translate or not
    private static final String KEY_OPEN_JIT = "preference_recite_open_jit";
    // is open recite words
    private static final String KEY_RECITE_OPEN = "preference_use_recite_or_not";
    //duration of tip
    private static final String KEY_DURATION_TIP_TIME = "DURATION_TIP_TIME";
    //is play mp3 auto
    private static final String KEY_PREFERENCE_AUTO_PLAY_SOUND = "preference_auto_play_sound";
    //当前背诵单词的 query
    private static final String KEY_CURRENT_CYCLIC = "CURRENT_CYCLIC";

    public ReciteTrayPref(@NonNull Context context) {
        super(context, "recite", 1);
    }

    /**
     * 是否开启背单词
     * @return
     */
    public boolean isReciteOpen() {
        return getBoolean(KEY_RECITE_OPEN, DEFAULT_IS_RECITE);
    }
    public void setReciteOpen(boolean isRecite) {
        put(KEY_RECITE_OPEN, isRecite);
    }

    /**
     * 单词提示时间
     * @return
     */
    public EIntervalTipTime getIntervalTipTime() {
        String name = getString(KEY_DURATION_TIP_TIME, EIntervalTipTime.THREE_MINUTE.name());
        return EIntervalTipTime.valueOf(name);
    }
    public void setEIntervalTipTime(String duration) {
        put(KEY_DURATION_TIP_TIME, duration);
    }

    public String getCurrentCyclicWord() {
        return getString(KEY_CURRENT_CYCLIC, "");
    }
    public void setCurrentCyclicWord(String query) {
        put(KEY_CURRENT_CYCLIC, query);
    }

    public EDurationTipTime getDurationTimeWay() {
        return EDurationTipTime.valueOf(getString(KEY_DURATION_TIP_TIME, EDurationTipTime.FOUR_SECOND.name()));
    }
    public void setDurationTipTime(String duration) {
        put(KEY_DURATION_TIP_TIME,duration);
    }

    public boolean isPlaySoundAuto(){
        return getBoolean(KEY_PREFERENCE_AUTO_PLAY_SOUND,false);
    }
    public void setPlaySoundAuto(boolean isPlaySound){
        put(KEY_PREFERENCE_AUTO_PLAY_SOUND,isPlaySound);
    }
}
