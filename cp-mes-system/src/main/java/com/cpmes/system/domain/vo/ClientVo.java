package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 客户信息视图对象 client
 *
 * @author cp-mes
 * @date 2024-03-05
 */
@Data
@ExcelIgnoreUnannotated
public class ClientVo {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @ExcelProperty(value = "客户ID")
    private Long clientId;

    /**
     * 客户编号
     */
    @ExcelProperty(value = "客户编号")
    private String clientNumber;

    /**
     * 客户名称
     */
    @ExcelProperty(value = "客户名称")
    private String clientName;

    /**
     * 客户简称
     */
    @ExcelProperty(value = "客户简称")
    private String clientAbbreviation;

    /**
     * 联系人
     */
    @ExcelProperty(value = "联系人")
    private String clientContact;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String clientPhone;

    /**
     * 客户地址
     */
    @ExcelProperty(value = "客户地址")
    private String clientAvatar;

    /**
     * 客户状态（0正常 1停用）
     */
    @ExcelProperty(value = "客户状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
