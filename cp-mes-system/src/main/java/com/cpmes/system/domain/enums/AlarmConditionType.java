package com.cpmes.system.domain.enums;

import lombok.Getter;

/**
 * 报警条件查询
 *
 * @Author cp-mes
 * @Date 2023/7/6 16:15
 */
@Getter
public enum AlarmConditionType {
    /**
     * 大于
     */
    G(0, ">"),

    /**
     * 等于
     */
    E(1, "="),
    /**
     * 小于
     */
    L(2, "<"),

    /**
     * 大于等于
     */
    GE(3, ">="),

    /**
     * 小于等于
     */
    LE(4, "<=");

    private final Integer code;
    private final String info;

    AlarmConditionType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }
}
