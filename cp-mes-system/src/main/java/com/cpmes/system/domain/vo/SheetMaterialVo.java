package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 工单物料视图对象 sheet_material
 *
 * @author cp-mes
 * @date 2024-03-29
 */
@Data
@ExcelIgnoreUnannotated
public class SheetMaterialVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long attachmentId;

    /**
     * 库存编码
     */
    @ExcelProperty(value = "库存编码")
    private String attachmentCode;

    /**
     * 库存名称
     */
    @ExcelProperty(value = "库存名称")
    private String attachmentName;

    /**
     * 库存规格
     */
    @ExcelProperty(value = "库存规格")
    private String model;

    /**
     * 库存数量
     */
    @ExcelProperty(value = "库存数量")
    private Integer quantity;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 供应商id
     */
    @ExcelProperty(value = "供应商id")
    private String purveyorId;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称")
    private String purveyorName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 库存类型
     */
    @ExcelProperty(value = "库存类型")
    private String type;

    /**
     * 工单ID
     */
    @ExcelProperty(value = "工单ID")
    private String sheetId;

    /**
     * 用量
     */
    @ExcelProperty(value = "用量")
    private Integer dosage;

    /**
     * 顺序
     */
    @ExcelProperty(value = "顺序")
    private Integer sequence;


}
