package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 工单物料对象 sheet_material
 *
 * @author cp-mes
 * @date 2024-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sheet_material")
public class SheetMaterial extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "attachment_id")
    private Long attachmentId;
    /**
     * 库存编码
     */
    private String attachmentCode;
    /**
     * 库存名称
     */
    private String attachmentName;
    /**
     * 库存规格
     */
    private String model;
    /**
     * 库存数量
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
     * 租户id
     */
    private String tenantId;
    /**
     * 库存类型
     */
    private String type;
    /**
     * 工单ID
     */
    private String sheetId;
    /**
     * 用量
     */
    private Integer dosage;
    /**
     * 顺序
     */
    private Integer sequence;

}
