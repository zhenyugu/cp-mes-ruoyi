package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 物料业务对象 material
 *
 * @author cp-mes
 * @date 2024-03-14
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long materialId;

    /**
     * 物料编号
     */
    @NotBlank(message = "物料编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String materialNumber;

    /**
     * 物料名称
     */
    @NotBlank(message = "物料名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String materialName;

    /**
     * 物料数量
     */
    @NotNull(message = "物料数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long materialQuantity;

    /**
     * 物料单位
     */
    @NotBlank(message = "物料单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String materialUnit;

    /**
     * 物料规格
     */
    @NotBlank(message = "物料规格不能为空", groups = { AddGroup.class, EditGroup.class })
    private String specification;

    /**
     * 物料属性
     */
    @NotBlank(message = "物料属性不能为空", groups = { AddGroup.class, EditGroup.class })
    private String materialAttribute;

    /**
     * 最大库存
     */
    @NotNull(message = "最大库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long inventoryMax;

    /**
     * 最小库存
     */
    @NotNull(message = "最小库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long inventoryMin;

    /**
     * 安全库存
     */
    @NotNull(message = "安全库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long inventorySafe;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
