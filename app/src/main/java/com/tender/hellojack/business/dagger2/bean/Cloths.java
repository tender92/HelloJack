package com.tender.hellojack.business.dagger2.bean;

/**
 * Created by boyu on 2018/3/23.
 */

public class Cloths {
    private Cloth cloth;

    public Cloths(Cloth cloth) {
        this.cloth = cloth;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    @Override
    public String toString() {
        return cloth.getColor() + "衣服";
    }
}
