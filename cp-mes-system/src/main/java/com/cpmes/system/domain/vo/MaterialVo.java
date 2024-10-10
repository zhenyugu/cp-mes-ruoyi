package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;


/**
 * 物料视图对象 material
 *
 * @author cp-mes
 * @date 2024-03-14
 */
@Data
@ExcelIgnoreUnannotated
public class MaterialVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long materialId;

    /**
     * 物料编号
     */
    @ExcelProperty(value = "物料编号")
    private String materialNumber;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称")
    private String materialName;

    /**
     * 物料数量
     */
    @ExcelProperty(value = "物料数量")
    private Long materialQuantity;

    /**
     * 物料单位
     */
    @ExcelProperty(value = "物料单位")
    private String materialUnit;

    /**
     * 物料规格
     */
    @ExcelProperty(value = "物料规格")
    private String specification;

    /**
     * 物料属性
     */
    @ExcelProperty(value = "物料属性")
    private String materialAttribute;

    /**
     * 最大库存
     */
    @ExcelProperty(value = "最大库存")
    private Long inventoryMax;

    /**
     * 最小库存
     */
    @ExcelProperty(value = "最小库存")
    private Long inventoryMin;

    /**
     * 安全库存
     */
    @ExcelProperty(value = "安全库存")
    private Long inventorySafe;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
