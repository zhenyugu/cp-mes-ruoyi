package com.cpmes.common.core.service;

/**
 * 通用 用户服务
 *
 * @author cp-mes
 */
public interface UserService {

    /**
     * 通过用户ID查询用户账户
     *
     * @param userId 用户ID
     * @return 用户账户
     */
    String selectUserNameById(Long userId);

}
