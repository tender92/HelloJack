package com.tender.hellojack.business.home.meterial.swipelistview;

/**
 * Created by boyu on 2018/4/10.
 */

public class CardTransBean {
    private String cardNo;
    private String transMoney;
    private String transTime;
    private String transStatus;
    private Runnable runnableRight;
    private Runnable runnableLeft;
    private Runnable runnableItem;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTransMoney() {
        return transMoney;
    }

    public void setTransMoney(String transMoney) {
        this.transMoney = transMoney;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public Runnable getRunnableRight() {
        return runnableRight;
    }

    public void setRunnableRight(Runnable runnableRight) {
        this.runnableRight = runnableRight;
    }

    public Runnable getRunnableLeft() {
        return runnableLeft;
    }

    public void setRunnableLeft(Runnable runnableLeft) {
        this.runnableLeft = runnableLeft;
    }

    public Runnable getRunnableItem() {
        return runnableItem;
    }

    public void setRunnableItem(Runnable runnableItem) {
        this.runnableItem = runnableItem;
    }
}
