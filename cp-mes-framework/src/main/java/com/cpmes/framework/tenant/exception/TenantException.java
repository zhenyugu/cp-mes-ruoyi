package com.cpmes.framework.tenant.exception;


import com.cpmes.common.exception.base.BaseException;

/**
 * 租户异常类
 *
 * @author cp-mes
 */
public class TenantException extends BaseException {

    private static final long serialVersionUID = 1L;

    public TenantException(String code, Object... args) {
        super("tenant", code, args, null);
    }
}
