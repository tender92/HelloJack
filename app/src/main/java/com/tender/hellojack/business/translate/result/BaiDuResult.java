package com.tender.hellojack.business.translate.result;

import com.tender.hellojack.model.translate.ETranslateFrom;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by boyu on 2018/1/31.
 */

public class BaiDuResult extends AbResult {

    private String from;
    private String to;
    private List<TranslateResult> trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<TranslateResult> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<TranslateResult> trans_result) {
        this.trans_result = trans_result;
    }

    @Override
    public List<String> wrapTranslation() {
        return wrapExplains();
    }

    @Override
    public List<String> wrapExplains() {
        final List<String> explains = new ArrayList<>();
        Observable.from(getTrans_result())
                .filter(new Func1<TranslateResult, Boolean>() {
                    @Override
                    public Boolean call(TranslateResult translateResult) {
                        return translateResult != null;
                    }
                })
                .subscribe(new Action1<TranslateResult>() {
                    @Override
                    public void call(TranslateResult translateResult) {
                        String result = translateResult.getDst();
                        explains.add(result);
                    }
                });

        return explains;
    }

    @Override
    public String wrapQuery() {
        final String[] query = {""};
        Observable.from(getTrans_result())
                .filter(new Func1<TranslateResult, Boolean>() {
                    @Override
                    public Boolean call(TranslateResult translateResult) {
                        return translateResult != null;
                    }
                })
                .subscribe(new Action1<TranslateResult>() {
                    @Override
                    public void call(TranslateResult translateResult) {
                        query[0] = translateResult.getSrc();
                    }
                });
        return query[0];
    }

    @Override
    public int wrapErrorCode() {
        return 0;
    }

    @Override
    public String wrapEnPhonetic() {
        return "";
    }

    @Override
    public String wrapAmPhonetic() {
        return "";
    }

    @Override
    public String wrapEnMp3() {
        return "";
    }

    @Override
    public String wrapAmMp3() {
        return "";
    }

    @Override
    public String translateFrom() {
        return ETranslateFrom.BAI_DU.name();
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

    private class TranslateResult {
        private String src;
        private String dst;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }
    }
}
