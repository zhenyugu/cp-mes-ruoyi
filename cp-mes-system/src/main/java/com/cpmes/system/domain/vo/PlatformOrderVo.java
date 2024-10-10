package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;


/**
 * 平台工单信息视图对象 platform_order
 *
 * @author cp-mes
 * @date 2024-01-31
 */
@Data
@ExcelIgnoreUnannotated
public class PlatformOrderVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 平台工单id
     */
    @ExcelProperty(value = "平台工单id")
    private Long platformOrderId;

    /**
     * 平台工单类型
     */
    @ExcelProperty(value = "平台工单类型")
    private String orderType;

    /**
     * 平台工单内容
     */
    @ExcelProperty(value = "平台工单内容")
    private String orderContent;

    /**
     * 平台工单附件ids
     */
    @ExcelProperty(value = "平台工单附件ids")
    private String ossIds;

    /**
     * 平台工单状态（0待处理，1已处理，2已取消）
     */
    @ExcelProperty(value = "平台工单状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=待处理，1已处理，2已取消")
    private String status;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
