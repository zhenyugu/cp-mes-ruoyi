package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.domain.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OSS对象存储对象
 *
 * @author cp-mes
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oss")
public class SysOss extends TenantEntity {

    /**
     * 对象存储主键
     */
    @TableId(value = "oss_id")
    private Long ossId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原名
     */
    private String originalName;

    /**
     * 文件后缀名
     */
    private String fileSuffix;

    /**
     * URL地址
     */
    private String url;

    /**
     * 服务商
     */
    private String service;

}
