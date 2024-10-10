package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 工单物料业务对象 sheet_material
 *
 * @author cp-mes
 * @date 2024-03-29
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SheetMaterialBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long attachmentId;

    /**
     * 库存编码
     */
    @NotBlank(message = "库存编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String attachmentCode;

    /**
     * 库存名称
     */
    @NotBlank(message = "库存名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String attachmentName;

    /**
     * 库存规格
     */
    @NotBlank(message = "库存规格不能为空", groups = { AddGroup.class, EditGroup.class })
    private String model;

    /**
     * 库存数量
     */
    @NotNull(message = "库存数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer quantity;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 供应商id
     */
    @NotBlank(message = "供应商id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String purveyorId;

    /**
     * 供应商名称
     */
    @NotBlank(message = "供应商名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String purveyorName;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

    /**
     * 库存类型
     */
    @NotBlank(message = "库存类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 工单ID
     */
    @NotBlank(message = "工单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sheetId;

    /**
     * 用量
     */
    @NotNull(message = "用量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer dosage;

    /**
     * 顺序
     */
    //    @NotBlank(message = "顺序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer sequence;


}
