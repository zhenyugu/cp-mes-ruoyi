package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 平台工单信息对象 platform_order
 *
 * @author cp-mes
 * @date 2024-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("platform_order")
public class PlatformOrder extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 平台工单id
     */
    @TableId(value = "platform_order_id")
    private Long platformOrderId;
    /**
     * 平台工单类型
     */
    private String orderType;
    /**
     * 平台工单内容
     */
    private String orderContent;
    /**
     * 平台工单附件ids
     */
    private String ossIds;
    /**
     * 平台工单状态（0待处理，1已处理，2已取消）
     */
    private String status;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 备注
     */
    private String remark;

}
