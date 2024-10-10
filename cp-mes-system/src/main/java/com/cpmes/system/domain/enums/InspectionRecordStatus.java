package com.cpmes.system.domain.enums;

import lombok.Getter;

/**
 * @Author cp-mes
 * @Date 2023/11/2 14:44
 **/
@Getter
public enum InspectionRecordStatus {
    /**
     * 进行中
     */
    IN_PROGRESS("0", "inProgress", "进行中"),
    /**
     * 已完成
     */
    COMPLETED("1", "completed", "已完成");

    private final String code;
    private final String info;
    private final String name;

    InspectionRecordStatus(String code, String info, String name) {
        this.code = code;
        this.info = info;
        this.name = name;
    }
}
