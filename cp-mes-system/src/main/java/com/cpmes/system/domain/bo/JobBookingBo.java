package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 报工业务对象 job_booking
 *
 * @author cp-mes
 * @date 2024-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class JobBookingBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long jobBookingId;

    /**
     * 工单编号
     */
    @NotBlank(message = "工单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sheetNumber;

    /**
     * 工序编号
     */
//    @NotBlank(message = "工序编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureNumber;

    /**
     * 产品编号
     */
    @NotBlank(message = "产品编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productNumber;

    /**
     * 工序名称
     */
    @NotBlank(message = "工序名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureName;

    /**
     * 工序状态
     */
//    @NotBlank(message = "工序状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureStatus;

    /**
     * 产品名称
     */
//    @NotBlank(message = "产品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

    /**
     * 产品规格
     */
//    @NotBlank(message = "产品规格不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productSpecification;

    /**
     * 报工数量
     */
//    @NotNull(message = "报工数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer jobBookingNum;

    /**
     * 计价方式
     */
//    @NotBlank(message = "计价方式不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pricingMethod;

    /**
     * 工资单价
     */
//    @NotNull(message = "工资单价不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer unitPrice;

    /**
     * 预计工资
     */
//    @NotNull(message = "预计工资不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer estimatedSalary;

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
     * 单位
     */
//    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 不良品项
     */
//    @NotBlank(message = "不良品项不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defectId;

    /**
     * 不良品项名
     */
//    @NotBlank(message = "不良品项名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defectiveNames;

    /**
     * 生产人员
     */
    @NotBlank(message = "生产人员不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productionPersonnel;

    /**
     * 生产人员名
     */
//    @NotBlank(message = "生产人员名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String personnelName;

    /**
     * 报工开始时间
     */
//    @NotNull(message = "报工开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startTime;

    /**
     * 报工结束时间
     */
//    @NotNull(message = "报工结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endTime;

    /**
     * 报工时长
     */
//    @NotBlank(message = "报工时长不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jobBookingCount;

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
