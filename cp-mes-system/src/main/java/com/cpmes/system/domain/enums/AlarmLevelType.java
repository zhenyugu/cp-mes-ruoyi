package com.cpmes.system.domain.enums;

import lombok.Getter;

/**
 * @Author cp-mes
 * @Date 2023/9/12 15:30
 */
@Getter
public enum AlarmLevelType {
    /**
     * 一般
     */
    KIND(0, "一般"),

    /**
     * 紧急
     */
    EMERGENT(1, "紧急"),
    /**
     * 严重
     */
    SERIOUS(2, "严重");

    private final Integer code;
    private final String info;

    AlarmLevelType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }
}
