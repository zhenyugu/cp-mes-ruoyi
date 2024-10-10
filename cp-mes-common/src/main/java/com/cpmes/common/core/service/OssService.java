package com.cpmes.common.core.service;

/**
 * 通用 OSS服务
 *
 * @author cp-mes
 */
public interface OssService {

    /**
     * 通过ossId查询对应的url
     *
     * @param ossIds ossId串逗号分隔
     * @return url串逗号分隔
     */
    String selectUrlByIds(String ossIds);

}
