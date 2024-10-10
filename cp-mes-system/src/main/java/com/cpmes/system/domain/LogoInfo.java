package com.cpmes.system.domain;
import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * logo信息对象 logo_info
 *
 * @author cp-mes
 * @date 2023-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("logo_info")
public class LogoInfo extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * logoID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 文件ID
     */
    private Long ossId;
    /**
     * logo类型（0：平台logo，1：浏览器logo）
     */
    private String type;
    /**
     * 删除标记
     */
    @TableLogic
    private String delFlag;

}
