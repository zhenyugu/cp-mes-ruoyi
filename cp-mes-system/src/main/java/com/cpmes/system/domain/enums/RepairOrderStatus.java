package com.cpmes.system.domain.enums;

import lombok.Getter;

/**
 * @Author cp-mes
 * @Date 2023/11/2 13:55
 **/
@Getter
public enum RepairOrderStatus {

    /**
     * 待进行
     */
    CARRIED_OUT("0", "carriedOut", "待进行"),
    /**
     * 进行中
     */
    IN_PROGRESS("1", "inProgress", "进行中"),
    /**
     * 已完成
     */
    COMPLETED("2", "completed", "已完成"),
    /**
     * 已取消
     */
    CANCELED("3", "canceled", "已取消");

    private final String code;
    private final String info;
    private final String name;

    RepairOrderStatus(String code, String info, String name) {
        this.code = code;
        this.info = info;
        this.name = name;
    }
}
