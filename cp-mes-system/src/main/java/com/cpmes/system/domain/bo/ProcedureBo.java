package com.cpmes.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;
import java.util.Map;


/**
 * 工序业务对象 procedure
 *
 * @author cp-mes
 * @date 2024-01-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProcedureBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long procedureId;

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
    @NotBlank(message = "报工人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reportingName;

    /**
     * 计划数默认值
     */
    @NotBlank(message = "计划数默认值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String plannedQuantity;

    /**
     * 报工数配比
     */
    @NotNull(message = "报工数配比不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer reportingRatio;

    /**
     * 不良品列表
     */
    @NotBlank(message = "不良品列表不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defectiveProducts;

    /**
     * 不良品名列表
     */
    @NotBlank(message = "不良品名列表不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defectiveNames;

    /**
     * 任务打卡
     */
//    @NotBlank(message = "任务打卡不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkIn;

    /**
     * 工艺要求
     */
//    @NotBlank(message = "工艺要求不能为空", groups = { AddGroup.class, EditGroup.class })
    private String technologicalRequirements;

    /**
     * 是否打印下料单
     */
    @NotBlank(message = "是否打印下料单不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isPrint;

    /**
     * 工序采集数据
     */
//    @NotBlank(message = "工序采集数据不能为空", groups = { AddGroup.class, EditGroup.class })
    private String collectionData;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

    /**
     * 自定义字段
     */
    private String customFields;


}
