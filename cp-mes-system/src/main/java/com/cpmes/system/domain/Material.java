package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 物料对象 material
 *
 * @author cp-mes
 * @date 2024-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("material")
public class Material extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "material_id")
    private Long materialId;
    /**
     * 物料编号
     */
    private String materialNumber;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料数量
     */
    private Long materialQuantity;
    /**
     * 物料单位
     */
    private String materialUnit;
    /**
     * 物料规格
     */
    private String specification;
    /**
     * 物料属性
     */
    private String materialAttribute;
    /**
     * 最大库存
     */
    private Long inventoryMax;
    /**
     * 最小库存
     */
    private Long inventoryMin;
    /**
     * 安全库存
     */
    private Long inventorySafe;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    /**
     * 备注
     */
    private String remark;

}
