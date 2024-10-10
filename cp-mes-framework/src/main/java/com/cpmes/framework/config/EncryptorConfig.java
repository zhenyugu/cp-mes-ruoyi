package com.cpmes.framework.config;

import com.cpmes.framework.config.properties.EncryptorProperties;
import com.cpmes.framework.manager.EncryptorManager;
import com.cpmes.framework.encrypt.MybatisDecryptInterceptor;
import com.cpmes.framework.encrypt.MybatisEncryptInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加解密配置
 *
 * @author 老马
 * @version 4.6.0
 */
@Configuration
@ConditionalOnProperty(value = "mybatis-encryptor.enable", havingValue = "true")
public class EncryptorConfig {

    @Autowired
    private EncryptorProperties properties;

    @Bean
    public EncryptorManager encryptorManager() {
        return new EncryptorManager();
    }

    @Bean
    public MybatisEncryptInterceptor mybatisEncryptInterceptor(EncryptorManager encryptorManager) {
        return new MybatisEncryptInterceptor(encryptorManager, properties);
    }

    @Bean
    public MybatisDecryptInterceptor mybatisDecryptInterceptor(EncryptorManager encryptorManager) {
        return new MybatisDecryptInterceptor(encryptorManager, properties);
    }
}
