package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.system.domain.SheetMaterial;
import com.cpmes.system.domain.Task;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 工单视图对象 sheet
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@Data
@ExcelIgnoreUnannotated
public class SheetVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long sheetId;

    /**
     * 工单编号
     */
    @ExcelProperty(value = "工单编号")
    private String sheetNumber;

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
     * 产品规格
     */
    @ExcelProperty(value = "产品规格")
    private String productSpecification;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 计划开始时间
     */
    @ExcelProperty(value = "计划开始时间")
    private Date planStart;

    /**
     * 计划结束时间
     */
    @ExcelProperty(value = "计划结束时间")
    private Date planEnd;

    /**
     * 计划数
     */
    @ExcelProperty(value = "计划数")
    private Integer plannedQuantity;

    /**
     * 良品数
     */
    @ExcelProperty(value = "良品数")
    private Integer goodQuantity;

    /**
     * 不良品数
     */
    @ExcelProperty(value = "不良品数")
    private Integer defectQuantity;

    /**
     * 关联单据
     */
    @ExcelProperty(value = "关联单据")
    private String relatedDocuments;

    /**
     * 状态（0未开始、1执行中、2已结束、3已取消）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=未开始、1执行中、2已结束、3已取消")
    private String status;

    /**
     * 二维码
     */
    @ExcelProperty(value = "二维码")
    private String qrcode;

    /**
     * 客户ID
     */
    private String clientId;
    /**
     * 客户名称
     */
    @ExcelProperty(value = "客户名称")
    private String clientName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * 工序集合
     */
    private Collection<Task> tasks;
    /**
     * 用料集合
     */
    private Collection<SheetMaterial> materials;
    /**
     * 自定义字段
     */
    private String customFields;
    /**
     * 选中开始时间
     */
    private String selectStart;

    /**
     * 选中结束时间
     */
    private String selectEnd;


}
