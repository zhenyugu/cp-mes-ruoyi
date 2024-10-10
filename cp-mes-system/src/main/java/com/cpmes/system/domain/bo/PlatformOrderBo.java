package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 平台工单信息业务对象 platform_order
 *
 * @author cp-mes
 * @date 2024-01-31
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class PlatformOrderBo extends BaseEntity {

    /**
     * 平台工单id
     */
    @NotNull(message = "平台工单id不能为空", groups = { EditGroup.class })
    private Long platformOrderId;

    /**
     * 平台工单类型
     */
//    @NotBlank(message = "平台工单类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderType;

    /**
     * 平台工单内容
     */
    @NotBlank(message = "平台工单内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderContent;

    /**
     * 平台工单附件ids
     */
//    @NotBlank(message = "平台工单附件ids不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ossIds;

    /**
     * 平台工单状态（0待处理，1已处理，2已取消）
     */
//    @NotBlank(message = "平台工单状态（0待处理，1已处理，2已取消）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 用户id
     */
//    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
