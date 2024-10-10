package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author cp-mes
 * @Date 2023/11/16 14:09
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant_package")
public class SysTenantPackage extends BaseEntity {
    /**
     * 租户套餐id
     */
    @TableId(value = "package_id")
    private Long packageId;
    /**
     * 套餐名称
     */
    private String packageName;
    /**
     * 关联菜单id
     */
    private String menuIds;
    /**
     * 备注
     */
    private String remark;
    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private Boolean menuCheckStrictly;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
}
