package com.tender.hellojack.business.translate.result;

import com.google.gson.annotations.SerializedName;
import com.tender.hellojack.model.translate.ETranslateFrom;

import java.util.Collections;
import java.util.List;

/**
 * Created by boyu on 2018/2/2.
 */

public class YouDaoResult extends AbResult {

    private String query;
    private List<String> translation;
    private BasicEntity basic;
    private int errorCode;
    private List<WebEntity> web;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public BasicEntity getBasic() {
        return basic;
    }

    public void setBasic(BasicEntity basic) {
        this.basic = basic;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<WebEntity> getWeb() {
        return web;
    }

    public void setWeb(List<WebEntity> web) {
        this.web = web;
    }

    @Override
    public List<String> wrapTranslation() {
        return getTranslation();
    }

    @Override
    public List<String> wrapExplains() {
        if(getBasic() != null) {
            return getBasic().getExplains();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public String wrapQuery() {
        return getQuery();
    }

    @Override
    public int wrapErrorCode() {
        return getErrorCode();
    }

    @Override
    public String wrapEnPhonetic() {
        if(getBasic() == null)return "";
        return getBasic().getUkPhonetic();
    }

    @Override
    public String wrapAmPhonetic() {
        if(getBasic() == null)return "";
        return getBasic().getUsPhonetic();
    }

    @Override
    public String wrapEnMp3() {
        if(getBasic() == null)return "";
        return getBasic().getUkSpeech();
    }

    @Override
    public String wrapAmMp3() {
        if(getBasic() == null)return "";
        return getBasic().getUsSpeech();
    }

    @Override
    public String translateFrom() {
        return ETranslateFrom.YOU_DAO.name();
    }

    @Override
    public String wrapPhEn() {
        if(getBasic() == null)return "";
        return getBasic().getUkPhonetic();
    }

    @Override
    public String wrapPhAm() {
        if(getBasic() == null)return "";
        return getBasic().getUsPhonetic();
    }

    @Override
    public String wrapMp3Name() {
        return "youdao_" + query + ".mp3";
    }

    private class BasicEntity {
        @SerializedName("us-phonetic")
        private String usPhonetic;
        @SerializedName("uk-speech")
        private String ukSpeech;
        @SerializedName("us-speech")
        private String usSpeech;
        private String speech;
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukPhonetic;
        private List<String> explains;

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUkPhonetic() {
            return ukPhonetic;
        }

        public void setUkPhonetic(String ukPhonetic) {
            this.ukPhonetic = ukPhonetic;
        }

        public String getUsPhonetic() {
            return usPhonetic;
        }

        public void setUsPhonetic(String usPhonetic) {
            this.usPhonetic = usPhonetic;
        }

        public String getSpeech() {
            return speech;
        }

        public void setSpeech(String speech) {
            this.speech = speech;
        }

        public String getUkSpeech() {
            return ukSpeech;
        }

        public void setUkSpeech(String ukSpeech) {
            this.ukSpeech = ukSpeech;
        }

        public String getUsSpeech() {
            return usSpeech;
        }

        public void setUsSpeech(String usSpeech) {
            this.usSpeech = usSpeech;
        }
    }

    private class WebEntity {
        private String key;
        private List<String> value;

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public List<String> getValue() {
            return value;
        }
    }
}
