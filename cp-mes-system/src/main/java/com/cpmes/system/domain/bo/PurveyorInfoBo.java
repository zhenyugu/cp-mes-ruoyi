package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 供应商库管理业务对象 purveyor_info
 *
 * @author cp-mes
 * @date 2023-09-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class PurveyorInfoBo extends BaseEntity {

    /**
     * 主键
     */
//    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long purveyorId;

    /**
     * 供应商编码
     */
    @NotBlank(message = "供应商编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String purveyorCode;

    /**
     * 供应商名称
     */
    @NotBlank(message = "供应商名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String purveyorName;

    /**
     * 供应商性质
     */
    @NotBlank(message = "供应商性质不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nature;

    /**
     * 供货类型
     */
//    @NotBlank(message = "供货类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String supplyType;

    /**
     * 联系人姓名
     */
//    @NotBlank(message = "联系人姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contractName;

    /**
     * 联系人职位
     */
//    @NotBlank(message = "联系人职位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contractPost;

    /**
     * 联系人电话
     */
//    @NotBlank(message = "联系人电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contractPhone;

    /**
     * 联系人邮箱
     */
//    @NotBlank(message = "联系人邮箱不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contractEmail;

    /**
     * 供应商地址
     */
//    @NotBlank(message = "供应商地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String address;

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

