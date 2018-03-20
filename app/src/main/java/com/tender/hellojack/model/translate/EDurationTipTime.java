package com.tender.hellojack.model.translate;

/**
 * Created by boyu on 2018/2/1.
 */

public enum EDurationTipTime {
    ONE_SECOND(0,2),
    THREE_SECOND(1,3),
    FOUR_SECOND(2,4),
    SIX_SECOND(3,6),
    TEN_SECOND(4,10);

    private int mIndex;
    private int mDurationTime;

    EDurationTipTime(int index,int time) {
        this.mIndex = index;
        this.mDurationTime = time;
    }

    public int getDurationTime() {
        return mDurationTime;
    }
    public int getIndex() {
        return mIndex;
    }
}
