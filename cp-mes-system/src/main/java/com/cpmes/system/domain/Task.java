package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 任务对象 task
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("task")
public class Task extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "task_id")
    private Long taskId;
    /**
     * 工单ID
     */
    private String sheetId;
    /**
     * 工单编号
     */
    private String sheetNumber;
    /**
     * 产品编号
     */
    private String productNumber;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品规格
     */
    private String productSpecification;
    /**
     * 工序ID
     */
    private String procedureId;
    /**
     * 工序编号
     */
    private String procedureNumber;
    /**
     * 工序名称
     */
    private String procedureName;
    /**
     * 报工权限
     */
    private String reportingAuthority;
    /**
     * 报工人
     */
    private String reportingName;
    /**
     * 分配列表
     */
    private String distributionList;
    /**
     * 报工数配比
     */
    private Integer reportingRatio;
    /**
     * 工序状态
     */
    private String procedureStatus;
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
     * 不良品列表
     */
    private String defectiveProducts;
    /**
     * 不良品列名表
     */
    private String defectiveNames;
    /**
     * 计划开始时间
     */
    private Date startTimePlan;
    /**
     * 计划结束时间
     */
    private Date endTimePlan;
    /**
     * 实际开始时间
     */
    private Date startTime;
    /**
     * 实际结束时间
     */
    private Date endTime;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 完成人
     */
    private String finishBy;
    /**
     * 完成人名
     */
    private String finishName;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 顺序
     */
    private Integer sequence;
    /**
     * 不良列表
     */
    private String defectList;

}
