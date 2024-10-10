package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;


/**
 * 工序对象 procedure
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("procedure_info")
public class Procedure extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "procedure_id")
    private Long procedureId;
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
     * 计划数默认值
     */
    private String plannedQuantity;
    /**
     * 报工数配比
     */
    private Integer reportingRatio;
    /**
     * 不良品列表
     */
    private String defectiveProducts;
    /**
     * 不良品名列表
     */
    private String defectiveNames;
    /**
     * 任务打卡
     */
    private String checkIn;
    /**
     * 工艺要求
     */
    private String technologicalRequirements;
    /**
     * 是否打印下料单
     */
    private String isPrint;
    /**
     * 工序采集数据
     */
    private String collectionData;
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
     * 自定义字段
     */
    private String customFields;



}
