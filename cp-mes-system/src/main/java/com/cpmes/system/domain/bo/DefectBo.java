package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 不良项业务对象 defect
 *
 * @author cp-mes
 * @date 2024-01-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DefectBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long defectId;

    /**
     * 不良项编号
     */
    @NotBlank(message = "不良项编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defectNumber;

    /**
     * 不良项名称
     */
    @NotBlank(message = "不良项名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defectName;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
