package com.tender.hellojack.business.translate.result;

import com.tender.hellojack.model.translate.ETranslateFrom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boyu on 2018/2/2.
 */

public class GoogleResult extends AbResult {

    /**
     * [[["谷歌","google",,,1]],,"en"]
     */
    private String translationResult;

    public String getTranslationResult() {
        return translationResult;
    }

    public void setTranslationResult(String translationResult) {
        this.translationResult = translationResult;
    }

    @Override
    public List<String> wrapTranslation() {
        return getTranslation();
    }

    @Override
    public List<String> wrapExplains() {
        return getTranslation();
    }

    @Override
    public String wrapQuery() {
        return getQuery();
    }

    @Override
    public int wrapErrorCode() {
        return 0;
    }

    @Override
    public String wrapEnPhonetic() {
        return null;
    }

    @Override
    public String wrapAmPhonetic() {
        return null;
    }

    @Override
    public String wrapEnMp3() {
        return null;
    }

    @Override
    public String wrapAmMp3() {
        return null;
    }

    @Override
    public String translateFrom() {
        return ETranslateFrom.GOOGLE.name();
    }

    @Override
    public String wrapPhEn() {
        return null;
    }

    @Override
    public String wrapPhAm() {
        return null;
    }

    @Override
    public String wrapMp3Name() {
        return null;
    }

    private List<String> getTranslation() {
        List<String> translationList = new ArrayList<>(1);
        String[] translation = translationResult.replace("[", "").split("\",");
        if(translation.length > 0){
            translationList.add(translation[0].replace("\"", ""));
        }
        return translationList;
    }

    private String getQuery() {
        String[] translation = translationResult.replace("[", "").split("\",");
        if(translation.length >1){
            return translation[1].replace("\"", "");
        }
        return "";
    }
}
