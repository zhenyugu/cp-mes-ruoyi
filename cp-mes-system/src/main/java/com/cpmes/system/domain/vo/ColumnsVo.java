package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.Data;

@Data
@ExcelIgnoreUnannotated
public class ColumnsVo {
    private static final long serialVersionUID = 1L;

    /**
     * 时间标签
     */
    private String timeLabel;

    /**
     * 计划数
     */
    private Integer plannedQuantity;

    /**
     * 良品数
     */
    private Integer goodQuantity;

    /**
     * 不良品数
     */
    private Integer defectQuantity;

    /**
     * 不良品率
     */
    private String percent;


}
