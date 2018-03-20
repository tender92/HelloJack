package com.tender.hellojack.business.translate.result;

import android.text.TextUtils;

import com.tender.hellojack.model.translate.ETranslateFrom;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by boyu on 2018/2/2.
 */

public class JinShanResult extends AbResult {

    private String word_name;
    private List<SymbolsEntity> symbols;
    private List<String> items;

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public List<SymbolsEntity> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<SymbolsEntity> symbols) {
        this.symbols = symbols;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public List<String> wrapTranslation() {
        return null;
    }

    @Override
    public List<String> wrapExplains() {
        final List<String> explains = new ArrayList<>();
        Observable.from(getSymbols())
                .first()
                .map(new Func1<SymbolsEntity, List<SymbolsEntity.PartsEntity>>() {
                    @Override
                    public List<SymbolsEntity.PartsEntity> call(SymbolsEntity symbolsEntity) {
                        return symbolsEntity.getParts();
                    }
                })
                .filter(new Func1<List<SymbolsEntity.PartsEntity>, Boolean>() {
                    @Override
                    public Boolean call(List<SymbolsEntity.PartsEntity> partsEntities) {
                        return partsEntities != null;
                    }
                })
                .flatMap(new Func1<List<SymbolsEntity.PartsEntity>, Observable<SymbolsEntity.PartsEntity>>() {
                    @Override
                    public Observable<SymbolsEntity.PartsEntity> call(List<SymbolsEntity.PartsEntity> partsEntities) {
                        return Observable.from(partsEntities);
                    }
                })
                .subscribe(new Action1<SymbolsEntity.PartsEntity>() {
                    @Override
                    public void call(SymbolsEntity.PartsEntity partsEntity) {
                        StringBuilder sb = new StringBuilder(partsEntity.getPart());
                        sb.append(",");
                        if(!partsEntity.getMeans().isEmpty()){
                            sb.append(partsEntity.getMeans().get(0));
                        }
                        explains.add(sb.toString());
                    }
                });
        return explains;
    }

    @Override
    public String wrapQuery() {
        return getWord_name();
    }

    @Override
    public int wrapErrorCode() {
        return 0;
    }

    @Override
    public String wrapEnPhonetic() {
        final String[] mp3 = {""};
        Observable.from(getSymbols())
                .first()
                .subscribe(new Action1<SymbolsEntity>() {
                    @Override
                    public void call(SymbolsEntity symbolsEntity) {
                        mp3[0] = symbolsEntity.getPh_en();
                    }
                });
        return mp3[0];
    }

    @Override
    public String wrapAmPhonetic() {
        final String[] mp3 = {""};
        Observable.from(getSymbols())
                .first()
                .subscribe(new Action1<SymbolsEntity>() {
                    @Override
                    public void call(SymbolsEntity symbolsEntity) {
                        mp3[0] = symbolsEntity.getPh_am();
                    }
                });
        return mp3[0];
    }

    @Override
    public String wrapEnMp3() {
        final String[] mp3 = {""};
        Observable.from(getSymbols())
                .first()
                .subscribe(new Action1<SymbolsEntity>() {
                    @Override
                    public void call(SymbolsEntity symbolEntity) {
                        mp3[0] = symbolEntity.ph_en_mp3;
                    }
                });
        return mp3[0];
    }

    @Override
    public String wrapAmMp3() {
        final String[] mp3 = {""};
        Observable.from(getSymbols())
                .first()
                .subscribe(new Action1<SymbolsEntity>() {
                    @Override
                    public void call(SymbolsEntity symbolEntity) {
                        mp3[0] = symbolEntity.ph_am_mp3;
                    }
                });
        return mp3[0];
    }

    @Override
    public String translateFrom() {
        return ETranslateFrom.JIN_SHAN.name();
    }

    @Override
    public String wrapPhEn() {
        final String[] phEn = {""};
        Observable.from(getSymbols())
                .first()
                .subscribe(new Action1<SymbolsEntity>() {
                    @Override
                    public void call(SymbolsEntity symbolEntity) {
                        phEn[0] = symbolEntity.ph_en;
                    }
                });
        return phEn[0];
    }

    @Override
    public String wrapPhAm() {
        final String[] phAm = {""};
        Observable.from(getSymbols())
                .first()
                .subscribe(new Action1<SymbolsEntity>() {
                    @Override
                    public void call(SymbolsEntity symbolEntity) {
                        phAm[0] = symbolEntity.ph_am;
                    }
                });
        return phAm[0];
    }

    @Override
    public String wrapMp3Name() {
        return getFileName(wrapEnMp3());
    }

    private String getFileName(String url) {
        String[] temp = url.split("/");
        String fileName = "";
        if(temp.length != 0){
            fileName = temp[temp.length-1];
        }
        if(!TextUtils.isEmpty(fileName) && fileName.endsWith(".mp3")){
            return fileName;
        }
        return null;
    }

    private class SymbolsEntity {
        private String ph_en;
        private String ph_am;
        private String ph_other;
        private String ph_en_mp3;
        private String ph_am_mp3;
        private String ph_tts_mp3;
        /**
         * means : ["走","离开","去做","进行"]
         */
        private List<PartsEntity> parts;

        public String getPh_en() {
            return ph_en;
        }

        public void setPh_en(String ph_en) {
            this.ph_en = ph_en;
        }

        public String getPh_am() {
            return ph_am;
        }

        public void setPh_am(String ph_am) {
            this.ph_am = ph_am;
        }

        public String getPh_other() {
            return ph_other;
        }

        public void setPh_other(String ph_other) {
            this.ph_other = ph_other;
        }

        public String getPh_en_mp3() {
            return ph_en_mp3;
        }

        public void setPh_en_mp3(String ph_en_mp3) {
            this.ph_en_mp3 = ph_en_mp3;
        }

        public String getPh_am_mp3() {
            return ph_am_mp3;
        }

        public void setPh_am_mp3(String ph_am_mp3) {
            this.ph_am_mp3 = ph_am_mp3;
        }

        public String getPh_tts_mp3() {
            return ph_tts_mp3;
        }

        public void setPh_tts_mp3(String ph_tts_mp3) {
            this.ph_tts_mp3 = ph_tts_mp3;
        }

        public List<PartsEntity> getParts() {
            return parts;
        }

        public void setParts(List<PartsEntity> parts) {
            this.parts = parts;
        }

        private class PartsEntity {
            private String part;
            private List<String> means;

            public String getPart() {
                return part;
            }

            public void setPart(String part) {
                this.part = part;
            }

            public List<String> getMeans() {
                return means;
            }

            public void setMeans(List<String> means) {
                this.means = means;
            }
        }
    }
}
