package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 备件库管理业务对象 attachment_info
 *
 * @author cp-mes
 * @date 2023-09-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AttachmentInfoBo extends BaseEntity {

    /**
     * 主键
     */
//    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long attachmentId;

    /**
     * 备件编码
     */
    @NotBlank(message = "备件编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String attachmentCode;

    /**
     * 备件名称
     */
    @NotBlank(message = "备件名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String attachmentName;

    /**
     * 备件型号
     */
    @NotBlank(message = "备件型号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String model;

    /**
     * 备件数量
     */
    private Integer quantity;

    /**
     * 单位
     */
//    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 供应商id
     */
//    @NotBlank(message = "供应商id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String purveyorId;

    /**
     * 供应商名称
     */
//    @NotBlank(message = "供应商名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String purveyorName;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

    /**
     * 库存类型
     */
    private String type;

}

