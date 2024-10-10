package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

/**
 * 任务业务对象 task
 *
 * @author cp-mes
 * @date 2024-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long taskId;

    /**
     * 工单ID
     */
    @NotBlank(message = "工单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sheetId;

    /**
     * 工单编号
     */
    @NotBlank(message = "工单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sheetNumber;


    /**
     * 产品编号
     */
    @NotBlank(message = "产品编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productNumber;

    /**
     * 产品名称
     */
    @NotBlank(message = "产品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

    /**
     * 产品规格
     */
    @NotBlank(message = "产品规格不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productSpecification;

    /**
     * 工序ID
     */
    @NotBlank(message = "工序ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureId;

    /**
     * 工序编号
     */
    @NotBlank(message = "工序编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureNumber;

    /**
     * 工序名称
     */
    @NotBlank(message = "工序名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureName;

    /**
     * 报工权限
     */
    @NotBlank(message = "报工权限不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reportingAuthority;

    /**
     * 报工人
     */
//    @NotBlank(message = "报工人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reportingName;

    /**
     * 分配列表
     */
//    @NotBlank(message = "分配列表不能为空", groups = { AddGroup.class, EditGroup.class })
    private String distributionList;

    /**
     * 报工数配比
     */
    @NotNull(message = "报工数配比不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer reportingRatio;

    /**
     * 工序状态
     */
    @NotBlank(message = "工序状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureStatus;

    /**
     * 计划数
     */
    @NotNull(message = "计划数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer plannedQuantity;

    /**
     * 良品数
     */
//    @NotNull(message = "良品数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer goodQuantity;

    /**
     * 不良品数
     */
//    @NotNull(message = "不良品数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer defectQuantity;

    /**
     * 不良品列表
     */
//    @NotBlank(message = "不良品列表不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defectiveProducts;

    /**
     * 不良品名列表不能为空
     */
    @NotBlank(message = "不良品名列表不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defectiveNames;

    /**
     * 计划开始时间
     */
//    @NotNull(message = "计划开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startTimePlan;

    /**
     * 计划结束时间
     */
//    @NotNull(message = "计划结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endTimePlan;

    /**
     * 实际开始时间
     */
//    @NotNull(message = "实际开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startTime;

    /**
     * 实际结束时间
     */
//    @NotNull(message = "实际结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endTime;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
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
    //    @NotBlank(message = "顺序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer sequence;

    /**
     * 不良列表
     */
    private String defectList;

}
