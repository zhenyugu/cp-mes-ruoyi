package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 备件库管理对象 attachment_info
 *
 * @author cp-mes
 * @date 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("attachment_info")
public class AttachmentInfo extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "attachment_id")
    private Long attachmentId;
    /**
     * 备件编码
     */
    private String attachmentCode;
    /**
     * 备件名称
     */
    private String attachmentName;
    /**
     * 备件型号
     */
    private String model;
    /**
     * 备件数量
     */
    private Integer quantity;
    /**
     * 单位
     */
    private String unit;
    /**
     * 供应商id
     */
    private String purveyorId;
    /**
     * 供应商名称
     */
    private String purveyorName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 库存类型
     */
    private String type;

}

