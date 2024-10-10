package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 自定义报视图对象 custom_report
 *
 * @author cp-mes
 * @date 2024-02-27
 */
@Data
@ExcelIgnoreUnannotated
public class DefectListVo {

    private static final long serialVersionUID = 1L;

    /**
     * 不良品Id
     */
    private String defectId;

    /**
     * 不良品名称
     */
    private String defectName;

    /**
     * 数量
     */
    private Integer number;

}
