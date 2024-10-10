package com.cpmes.system.domain.bo;

import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * logo信息业务对象 logo_info
 *
 * @author cp-mes
 * @date 2023-11-01
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class LogoInfoBo extends BaseEntity {

    /**
     * logoID
     */
//    @NotNull(message = "logoID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 文件ID
     */
    @NotNull(message = "文件ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ossId;

    /**
     * logo类型（0：平台logo，1：浏览器logo）
     */
    @NotBlank(message = "logo类型（0：平台logo，1：浏览器logo）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;


}
