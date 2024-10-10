package com.cpmes.system.domain.vo;

import java.util.Date;

import com.cpmes.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 备件库管理视图对象 attachment_info
 *
 * @author cp-mes
 * @date 2023-09-11
 */
@Data
@ExcelIgnoreUnannotated
public class AttachmentInfoVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long attachmentId;

    /**
     * 备件编码
     */
    @ExcelProperty(value = "备件编码")
    private String attachmentCode;

    /**
     * 备件名称
     */
    @ExcelProperty(value = "备件名称")
    private String attachmentName;

    /**
     * 备件型号
     */
    @ExcelProperty(value = "备件型号")
    private String model;

    /**
     * 备件数量
     */
    @ExcelProperty(value = "备件数量")
    private Integer quantity;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 供应商id
     */
    @ExcelProperty(value = "供应商id")
    private String purveyorId;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称")
    private String purveyorName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 库存类型
     */
    private String type;

}

