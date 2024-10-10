package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 客户信息对象 client
 *
 * @author cp-mes
 * @date 2024-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("client")
public class Client extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 客户ID
     */
    @TableId(value = "client_id")
    private Long clientId;
    /**
     * 客户编号
     */
    private String clientNumber;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 客户简称
     */
    private String clientAbbreviation;
    /**
     * 联系人
     */
    private String clientContact;
    /**
     * 联系电话
     */
    private String clientPhone;
    /**
     * 客户地址
     */
    private String clientAvatar;
    /**
     * 客户状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    /**
     * 备注
     */
    private String remark;

}
