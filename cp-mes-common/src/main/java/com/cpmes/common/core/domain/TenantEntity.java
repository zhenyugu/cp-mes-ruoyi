package com.cpmes.common.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author cp-mes
 * @Date 2023/11/16 10:04
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    /**
     * 租户编号
     */
    private String tenantId;

}
