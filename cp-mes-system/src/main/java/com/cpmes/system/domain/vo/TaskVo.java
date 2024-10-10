package com.cpmes.system.domain.vo;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;


/**
 * 任务视图对象 task
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@Data
@ExcelIgnoreUnannotated
public class TaskVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long taskId;

    /**
     * 工单ID
     */
    @ExcelProperty(value = "工单ID")
    private String sheetId;

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
     * 工序ID
     */
    @ExcelProperty(value = "工序ID")
    private String procedureId;

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
    @ExcelProperty(value = "报工权限")
    private String reportingAuthority;

    /**
     * 报工人
     */
    @ExcelProperty(value = "报工人")
    private String reportingName;

    /**
     * 分配列表
     */
    @ExcelProperty(value = "分配列表")
    private String distributionList;

    /**
     * 报工数配比
     */
    @ExcelProperty(value = "报工数配比")
    private Integer reportingRatio;

    /**
     * 工序状态
     */
    @ExcelProperty(value = "工序状态")
    private String procedureStatus;

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
     * 计划开始时间
     */
    @ExcelProperty(value = "计划开始时间")
    private Date startTimePlan;

    /**
     * 计划结束时间
     */
    @ExcelProperty(value = "计划结束时间")
    private Date endTimePlan;

    /**
     * 实际开始时间
     */
    @ExcelProperty(value = "实际开始时间")
    private Date startTime;

    /**
     * 实际结束时间
     */
    @ExcelProperty(value = "实际结束时间")
    private Date endTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 完成人
     */
    @ExcelProperty(value = "完成人")
    private String finishBy;

    /**
     * 完成人名
     */
    @ExcelProperty(value = "完成人名")
    private String finishName;

    /**
     * 完成时间
     */
    @ExcelProperty(value = "完成时间")
    private Date finishTime;

    /**
     * 顺序
     */
    @ExcelProperty(value = "顺序")
    private Integer sequence;

    /**
     * 日期
     */
    private String dateTime;

    /**
     * 未开始
     */
    private Long notStart;

    /**
     * 已结束
     */
    private Long finish;

    /**
     * 执行中
     */
    private Long loading;

    /**
     * 任务名
     */
    private String name;

    /**
     * 任务数量
     */
    private Long value;

    /**
     * 未完成任务数量
     */
    private Long unfinished;

    /**
     * 不良列表
     */
    private String defectList;






}
