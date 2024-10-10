package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * 不良项视图对象 defect
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Data
@ExcelIgnoreUnannotated
public class DefectVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long defectId;

    /**
     * 不良项编号
     */
    @ExcelProperty(value = "不良项编号")
    private String defectNumber;

    /**
     * 不良项名称
     */
    @ExcelProperty(value = "不良项名称")
    private String defectName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    List<ColumnsVo> columns;

    private String percent;


}
