package com.cpmes.system.domain.vo;

import java.util.Date;
import java.util.List;

import com.cpmes.common.core.domain.BaseEntity;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 报工视图对象 job_booking
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@Data
@ExcelIgnoreUnannotated
public class JobBookingVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
//    @ExcelProperty(value = "主键ID")
    private Long jobBookingId;

    /**
     * 工单编号
     */
    @ExcelProperty(value = "工单编号")
    private String sheetNumber;

    /**
     * 工序编号
     */
    @ExcelProperty(value = "工序编号")
    private String procedureNumber;

    /**
     * 产品编号
     */
    @ExcelProperty(value = "产品编号")
    private String productNumber;

    /**
     * 工序名称
     */
    @ExcelProperty(value = "工序名称")
    private String procedureName;

    /**
     * 工序状态
     */
//    @ExcelProperty(value = "工序状态")
    private String procedureStatus;

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
     * 报工数量
     */
    @ExcelProperty(value = "报工数量")
    private Integer jobBookingNum;

    /**
     * 计价方式
     */
    @ExcelProperty(value = "计价方式")
    private String pricingMethod;

    /**
     * 工资单价
     */
    @ExcelProperty(value = "工资单价")
    private Integer unitPrice;

    /**
     * 预计工资
     */
    @ExcelProperty(value = "预计工资")
    private Integer estimatedSalary;

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
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 不良品项
     */
//    @ExcelProperty(value = "不良品项")
    private String defectId;

    /**
     * 不良品项名
     */
    @ExcelProperty(value = "不良品项名")
    private String defectiveNames;

    /**
     * 生产人员
     */
//    @ExcelProperty(value = "生产人员")
    private String productionPersonnel;

    /**
     * 生产人员名
     */
    @ExcelProperty(value = "生产人员名")
    private String personnelName;

    /**
     * 报工开始时间
     */
    @ExcelProperty(value = "报工开始时间")
    private Date startTime;

    /**
     * 报工结束时间
     */
    @ExcelProperty(value = "报工结束时间")
    private Date endTime;

    /**
     * 报工时长
     */
    @ExcelProperty(value = "报工时长")
    private String jobBookingCount;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 自定义字段
     */
    private String customFields;

    private String percent;

    private List<ColumnsVo> columns;

    /**
     * 报工数量
     */
    private Long  jobBookingCountNum;


}
