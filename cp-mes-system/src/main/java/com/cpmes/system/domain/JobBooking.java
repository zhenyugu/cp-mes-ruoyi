package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 报工对象 job_booking
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_booking")
public class JobBooking extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "job_booking_id")
    private Long jobBookingId;
    /**
     * 工单编号
     */
    private String sheetNumber;
    /**
     * 工序编号
     */
    private String procedureNumber;
    /**
     * 产品编号
     */
    private String productNumber;
    /**
     * 工序名称
     */
    private String procedureName;
    /**
     * 工序状态
     */
    private String procedureStatus;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品规格
     */
    private String productSpecification;
    /**
     * 报工数量
     */
    private Integer jobBookingNum;
    /**
     * 计价方式
     */
    private String pricingMethod;
    /**
     * 工资单价
     */
    private Integer unitPrice;
    /**
     * 预计工资
     */
    private Integer estimatedSalary;
    /**
     * 良品数
     */
    private Integer goodQuantity;
    /**
     * 不良品数
     */
    private Integer defectQuantity;
    /**
     * 单位
     */
    private String unit;
    /**
     * 不良品项
     */
    private String defectId;
    /**
     * 不良品项名
     */
    private String defectiveNames;
    /**
     * 生产人员
     */
    private String productionPersonnel;
    /**
     * 生产人员名
     */
    private String personnelName;
    /**
     * 报工开始时间
     */
    private Date startTime;
    /**
     * 报工结束时间
     */
    private Date endTime;
    /**
     * 报工时长
     */
    private String jobBookingCount;
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
