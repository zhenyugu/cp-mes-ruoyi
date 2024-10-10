package com.cpmes.common.core.domain.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 短信登录对象
 *
 * @author cp-mes
 */

@Data
public class SmsLoginBody {

    /**
     * 租户ID
     */
    @NotBlank(message = "{tenant.number.not.blank}")
    private String tenantId;

    /**
     * 用户名
     */
    @NotBlank(message = "{user.phonenumber.not.blank}")
    private String phonenumber;

    /**
     * 用户密码
     */
    @NotBlank(message = "{sms.code.not.blank}")
    private String smsCode;

}
