package com.cpmes.system.domain.enums;

import lombok.Getter;

/**
 * MQTT消息主题枚举类
 *
 * @Author cp-mes
 * @Date 2023/4/21 18:09
 */
@Getter
public enum TopicType {
    /**
     * 电压
     */
    ELECTRIC_U(0, "electric/voltage","电压"),

    /**
     * 电流
     */
    ELECTRIC_I(1, "electric/current","电流"),

    /**
     * 电功率
     */
    ELECTRIC_P(2, "electric/power","电功率"),

    /**
     * 用电量
     */
    ELECTRIC_W(3, "electric/consumption","用电量"),

    /**
     * 用水量
     */
    WATER_CONSUMPTION(4, "water/consumption","用水量");

    private final Integer code;
    private final String info;
    private final String desc;

    TopicType(Integer code, String info,String desc) {
        this.code = code;
        this.info = info;
        this.desc = desc;
    }
}
