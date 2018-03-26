package com.tender.hellojack.business.dagger2.bean;

import javax.inject.Inject;

/**
 * Created by boyu on 2018/3/23.
 */

public class Shoe {
    @Inject
    public Shoe() {
    }

    @Override
    public String toString() {
        return "鞋子";
    }
}
