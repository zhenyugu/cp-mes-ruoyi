package com.cpmes.framework.tenant.manager;

import com.cpmes.common.constant.GlobalConstants;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.framework.manager.PlusSpringCacheManager;
import org.springframework.cache.Cache;

/**
 * 重写 cacheName 处理方法 支持多租户
 *
 * @author cp-mes
 */
public class TenantSpringCacheManager extends PlusSpringCacheManager {

    public TenantSpringCacheManager() {
    }

    @Override
    public Cache getCache(String name) {
        if (StringUtils.contains(name, GlobalConstants.GLOBAL_REDIS_KEY)) {
            return super.getCache(name);
        }
        return super.getCache(name);
//        String tenantId = TenantHelper.getTenantId();
//        if (StringUtils.startsWith(name, tenantId)) {
//            // 如果存在则直接返回
//            return super.getCache(name);
//        }
//        return super.getCache(tenantId + ":" + name);
    }

}
