package com.cpmes.framework.tenant.config;

import cn.dev33.satoken.dao.SaTokenDao;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.cpmes.framework.tenant.core.TenantSaTokenDao;
import com.cpmes.framework.tenant.handle.PlusTenantLineHandler;
import com.cpmes.framework.tenant.manager.TenantSpringCacheManager;
import com.cpmes.framework.tenant.properties.TenantProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户配置类
 *
 * @author cp-mes
 */
@EnableConfigurationProperties(TenantProperties.class)
@Configuration()
@ConditionalOnProperty(value = "tenant.enable", havingValue = "true")
public class TenantConfig {

    /**
     * 初始化租户配置
     */
    @Bean
    public boolean tenantInit(MybatisPlusInterceptor mybatisPlusInterceptor,
                              TenantProperties tenantProperties) {
        List<InnerInterceptor> interceptors = new ArrayList<>();
        // 多租户插件 必须放到第一位
        interceptors.add(tenantLineInnerInterceptor(tenantProperties));
        interceptors.addAll(mybatisPlusInterceptor.getInterceptors());
        mybatisPlusInterceptor.setInterceptors(interceptors);
        return true;
    }

    /**
     * 多租户插件
     */
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties tenantProperties) {
        return new TenantLineInnerInterceptor(new PlusTenantLineHandler(tenantProperties));
    }

//    @Bean
//    public RedissonAutoConfigurationCustomizer tenantRedissonCustomizer(RedissonProperties redissonProperties) {
//        return config -> {
//            TenantKeyPrefixHandler nameMapper = new TenantKeyPrefixHandler(redissonProperties.getKeyPrefix());
//            SingleServerConfig singleServerConfig = ReflectUtils.invokeGetter(config, "singleServerConfig");
//            if (ObjectUtil.isNotNull(singleServerConfig)) {
//                // 使用单机模式
//                // 设置多租户 redis key前缀
//                singleServerConfig.setNameMapper(nameMapper);
//                ReflectUtils.invokeSetter(config, "singleServerConfig", singleServerConfig);
//            }
//            ClusterServersConfig clusterServersConfig = ReflectUtils.invokeGetter(config, "clusterServersConfig");
//            // 集群配置方式 参考下方注释
//            if (ObjectUtil.isNotNull(clusterServersConfig)) {
//                // 设置多租户 redis key前缀
//                clusterServersConfig.setNameMapper(nameMapper);
//                ReflectUtils.invokeSetter(config, "clusterServersConfig", clusterServersConfig);
//            }
//        };
//    }

    /**
     * 多租户缓存管理器
     */
    @Primary
    @Bean
    public CacheManager tenantCacheManager() {
        return new TenantSpringCacheManager();
    }

    /**
     * 多租户鉴权dao实现
     */
    @Primary
    @Bean
    public SaTokenDao tenantSaTokenDao() {
        return new TenantSaTokenDao();
    }

}
