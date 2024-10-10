package com.cpmes.system.domain.enums;

import lombok.Getter;

/**
 * @Author cp-mes
 * @Date 2023/8/24 16:05
 **/
@Getter
public enum EnergyType {
    /**
     * 电能
     */
    ELECTRICITY("0", "electricityw"),

    /**
     * 水
     */
    WATER("1", "water"),

    /**
     * 电功率
     */
    ELECTRICITYP("2", "electricityp"),
    /**
     * 电流
     */
    ELECTRICITYI("3", "electricityi"),
    /**
     * 电功率
     */
    ELECTRICITYU("4", "electricityu");



    private final String code;
    private final String info;

    EnergyType(String code, String info) {
        this.code = code;
        this.info = info;
    }
}
