package com.tender.hellojack.business.dagger2.bean;

/**
 * Created by boyu on 2018/3/23.
 */

public class ClothHandler {
    public Cloths handle(Cloth cloth) {
        return new Cloths(cloth);
    }
}
