package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 不良项对象 defect
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("defect")
public class Defect extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "defect_id")
    private Long defectId;
    /**
     * 不良项编号
     */
    private String defectNumber;
    /**
     * 不良项名称
     */
    private String defectName;
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
