package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;


/**
 * 产品视图对象 product
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Data
@ExcelIgnoreUnannotated
public class ProductVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long productId;

    /**
     * 产品编号
     */
    @ExcelProperty(value = "产品编号")
    private String productNumber;

    /**
     * 产品名称
     */
    @ExcelProperty(value = "产品名称")
    private String productName;

    /**
     * 库存数量
     */
    @ExcelProperty(value = "库存数量")
    private Integer productQuantity;

    /**
     * 库存单位
     */
    @ExcelProperty(value = "库存单位")
    private String productUnit;

    /**
     * 产品规格
     */
    @ExcelProperty(value = "产品规格")
    private String specification;

    /**
     * 产品属性
     */
    @ExcelProperty(value = "产品属性")
    private String productAttribute;

    /**
     * 最大库存
     */
    @ExcelProperty(value = "最大库存")
    private Integer inventoryMax;

    /**
     * 最小库存
     */
    @ExcelProperty(value = "最小库存")
    private Integer inventoryMin;

    /**
     * 安全库存
     */
    @ExcelProperty(value = "安全库存")
    private Integer inventorySafe;

    /**
     * 工艺路线
     */
    @ExcelProperty(value = "工艺路线")
    private String routeId;

    /**
     * 工艺路线名称
     */
    @ExcelProperty(value = "工艺路线名称")
    private String routeName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 自定义字段
     */
    private String customFields;

    List<ColumnsVo> columns;


}
