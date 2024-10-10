package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 产品业务对象 product
 *
 * @author cp-mes
 * @date 2024-01-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long productId;

    /**
     * 产品编号
     */
    @NotBlank(message = "产品编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productNumber;

    /**
     * 产品名称
     */
    @NotBlank(message = "产品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

    /**
     * 库存数量
     */
    @NotNull(message = "库存数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer productQuantity;

    /**
     * 库存单位
     */
    @NotBlank(message = "库存单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productUnit;

    /**
     * 产品规格
     */
    @NotBlank(message = "产品规格不能为空", groups = { AddGroup.class, EditGroup.class })
    private String specification;

    /**
     * 产品属性
     */
    @NotBlank(message = "产品属性不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productAttribute;

    /**
     * 最大库存
     */
    @NotNull(message = "最大库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer inventoryMax;

    /**
     * 最小库存
     */
    @NotNull(message = "最小库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer inventoryMin;

    /**
     * 安全库存
     */
    @NotNull(message = "安全库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer inventorySafe;

    /**
     * 工艺路线
     */
//    @NotBlank(message = "工艺路线不能为空", groups = { AddGroup.class, EditGroup.class })
    private String routeId;

    /**
     * 工艺路线名称
     */
    private String routeName;

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
