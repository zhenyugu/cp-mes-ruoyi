package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 工单对象 sheet
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sheet")
public class Sheet extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "sheet_id")
    private Long sheetId;
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
     * 单位
     */
    private String unit;
    /**
     * 计划开始时间
     */
    private Date planStart;
    /**
     * 计划结束时间
     */
    private Date planEnd;
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
     * 关联单据
     */
    private String relatedDocuments;
    /**
     * 状态（0未开始、1执行中、2已结束、3已取消）
     */
    private String status;
    /**
     * 二维码
     */
    private String qrcode;
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
    /**
     * 客户ID
     */
    private String clientId;
    /**
     * 客户名称
     */
    private String clientName;

}
