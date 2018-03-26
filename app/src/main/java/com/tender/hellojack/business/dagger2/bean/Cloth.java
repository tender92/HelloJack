package com.tender.hellojack.business.dagger2.bean;

/**
 * Created by boyu on 2018/3/23.
 */

public class Cloth {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color + "布料";
    }
}
