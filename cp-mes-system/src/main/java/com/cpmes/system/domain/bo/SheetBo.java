package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import com.cpmes.system.domain.SheetMaterial;
import com.cpmes.system.domain.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 工单业务对象 sheet
 *
 * @author cp-mes
 * @date 2024-01-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SheetBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long sheetId;


    /**
     * 工单编号
     */
    @NotBlank(message = "工单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sheetNumber;

    /**
     * 产品编号
     */
    @NotBlank(message = "产品编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productNumber;

    /**
     * 产品名称
     */
//    @NotBlank(message = "产品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

    /**
     * 产品规格
     */
//    @NotBlank(message = "产品规格不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productSpecification;

    /**
     * 单位
     */
//    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 计划开始时间
     */
    @NotNull(message = "计划开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date planStart;

    /**
     * 计划结束时间
     */
    @NotNull(message = "计划结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date planEnd;

    /**
     * 计划数
     */
    @NotNull(message = "计划数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer plannedQuantity;

    /**
     * 良品数
     */
//    @NotNull(message = "良品数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer goodQuantity;

    /**
     * 不良品数
     */
//    @NotNull(message = "不良品数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer defectQuantity;

    /**
     * 关联单据
     */
//    @NotBlank(message = "关联单据不能为空", groups = { AddGroup.class, EditGroup.class })
    private String relatedDocuments;

    /**
     * 状态（0未开始、1执行中、2已结束、3已取消）
     */
//    @NotBlank(message = "状态（0未开始、1执行中、2已结束、3已取消）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 二维码
     */
//    @NotBlank(message = "二维码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String qrcode;

    /**
     * 客户ID
     */
    private String clientId;
    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

    /**
     * 工序集合
     */
    private Collection<Task> tasks;
    /**
     * 用料集合
     */
    private Collection<SheetMaterial> materials;

    /**
     * 自定义字段
     */
    private String customFields;

    /**
     * 选中开始时间
     */
    private String selectStart;

    /**
     * 选中结束时间
     */
    private String selectEnd;

    /**
     * 选中状态集合
     */
    private List<String> queryStatus;


}
