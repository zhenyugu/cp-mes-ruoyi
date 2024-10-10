package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 产品对象 product
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product")
public class Product extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "product_id")
    private Long productId;
    /**
     * 产品编号
     */
    private String productNumber;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 库存数量
     */
    private Integer productQuantity;
    /**
     * 库存单位
     */
    private String productUnit;
    /**
     * 产品规格
     */
    private String specification;
    /**
     * 产品属性
     */
    private String productAttribute;
    /**
     * 最大库存
     */
    private Integer inventoryMax;
    /**
     * 最小库存
     */
    private Integer inventoryMin;
    /**
     * 安全库存
     */
    private Integer inventorySafe;
    /**
     * 工艺路线
     */
    private String routeId;
    /**
     * 工艺路线名称
     */
    private String routeName;
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
