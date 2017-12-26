package com.tender.hellojack.model.enums;

/**
 * Created by boyu on 2017/12/25.
 */

public enum GenderEnum {
    UNKNOWN(0),
    MALE(1),
    FEMALE(2);

    private Integer value;

    GenderEnum(Integer value) {
        this.value = value;
    }

    public static GenderEnum genderOfValue(int status) {
        for (GenderEnum e : values()) {
            if (e.getValue() == status) {
                return e;
            }
        }
        return UNKNOWN;
    }

    private Integer getValue() {
        return value;
    }
}
