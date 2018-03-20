package com.tender.hellojack.model.translate;

/**
 * Created by boyu on 2018/2/1.
 */

public enum EIntervalTipTime {
    THIRTY_SECOND(0,30),
    ONE_MINUTE(1,1),
    THREE_MINUTE(2,3),
    FIVE_MINUTE(3,5),
    TEN_MINUTE(4,10),
    THIRTY_MINUTE(5,30);

    private int mIntervalTime;
    private int mIndex;

    EIntervalTipTime(int index,int time) {
        this.mIndex = index;
        this.mIntervalTime = time;
    }

    public int getIntervalTime() {
        return mIntervalTime;
    }

    public int getIndex() {
        return mIndex;
    }
}
