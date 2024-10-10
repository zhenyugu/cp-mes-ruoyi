package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 客户信息业务对象 client
 *
 * @author cp-mes
 * @date 2024-03-05
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientBo extends BaseEntity {

    /**
     * 客户ID
     */
    @NotNull(message = "客户ID不能为空", groups = { EditGroup.class })
    private Long clientId;

    /**
     * 客户编号
     */
    @NotBlank(message = "客户编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientNumber;

    /**
     * 客户名称
     */
    @NotBlank(message = "客户名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientName;

    /**
     * 客户简称
     */
//    @NotBlank(message = "客户简称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientAbbreviation;

    /**
     * 联系人
     */
//    @NotBlank(message = "联系人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientContact;

    /**
     * 联系电话
     */
//    @NotBlank(message = "联系电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientPhone;

    /**
     * 客户地址
     */
//    @NotBlank(message = "客户地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientAvatar;

    /**
     * 客户状态（0正常 1停用）
     */
//    @NotBlank(message = "客户状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 备注
     */
//    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
