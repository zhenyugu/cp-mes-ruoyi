package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Map;


/**
 * 工序视图对象 procedure
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Data
@ExcelIgnoreUnannotated
public class ProcedureVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long procedureId;

    /**
     * 工序编号
     */
    @ExcelProperty(value = "工序编号")
    private String procedureNumber;

    /**
     * 工序名称
     */
    @ExcelProperty(value = "工序名称")
    private String procedureName;

    /**
     * 报工权限
     */
    private String reportingAuthority;

    /**
     * 报工人
     */
    @ExcelProperty(value = "报工权限")
    private String reportingName;

    /**
     * 计划数默认值
     */
    @ExcelProperty(value = "计划数默认值")
    private String plannedQuantity;

    /**
     * 报工数配比
     */
    @ExcelProperty(value = "报工数配比")
    private Integer reportingRatio;

    /**
     * 不良品列表
     */
    @ExcelProperty(value = "不良品列表")
    private String defectiveProducts;

    /**
     * 不良品名列表
     */
    @ExcelProperty(value = "不良品名列表")
    private String defectiveNames;

    /**
     * 任务打卡
     */
    @ExcelProperty(value = "任务打卡")
    private String checkIn;

    /**
     * 工艺要求
     */
    @ExcelProperty(value = "工艺要求")
    private String technologicalRequirements;

    /**
     * 是否打印下料单
     */
    @ExcelProperty(value = "是否打印下料单")
    private String isPrint;

    /**
     * 工序采集数据
     */
    @ExcelProperty(value = "工序采集数据")
    private String collectionData;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 自定义字段
     */
    private String customFields;


}
