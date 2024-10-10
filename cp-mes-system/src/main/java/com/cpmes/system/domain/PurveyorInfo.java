package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 供应商库管理对象 purveyor_info
 *
 * @author cp-mes
 * @date 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("purveyor_info")
public class PurveyorInfo extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "purveyor_id")
    private Long purveyorId;
    /**
     * 供应商编码
     */
    private String purveyorCode;
    /**
     * 供应商名称
     */
    private String purveyorName;
    /**
     * 供应商性质
     */
    private String nature;
    /**
     * 供货类型
     */
    private String supplyType;
    /**
     * 联系人姓名
     */
    private String contractName;
    /**
     * 联系人职位
     */
    private String contractPost;
    /**
     * 联系人电话
     */
    private String contractPhone;
    /**
     * 联系人邮箱
     */
    private String contractEmail;
    /**
     * 供应商地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;
    /**
     * 库存类型
     */
    private String type;

}

