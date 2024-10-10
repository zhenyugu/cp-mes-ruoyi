package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 工艺路线业务对象 process_route
 *
 * @author cp-mes
 * @date 2024-01-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessRouteBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long processRouteId;

    /**
     * 工艺路线编号
     */
    @NotBlank(message = "工艺路线编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processRouteNumber;

    /**
     * 工艺路线名称
     */
    @NotBlank(message = "工艺路线名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processRouteName;

    /**
     * 工序
     */
//    @NotBlank(message = "工序不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureIds;

    /**
     * 工序名
     */
//    @NotBlank(message = "工序名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procedureNames;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
