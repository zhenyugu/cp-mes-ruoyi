package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 工艺路线对象 process_route
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("process_route")
public class ProcessRoute extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "process_route_id")
    private Long processRouteId;
    /**
     * 工艺路线编号
     */
    private String processRouteNumber;
    /**
     * 工艺路线名称
     */
    private String processRouteName;
    /**
     * 工序
     */
    private String procedureIds;
    /**
     * 工序名
     */
    private String procedureNames;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    /**
     * 备注
     */
    private String remark;

}
