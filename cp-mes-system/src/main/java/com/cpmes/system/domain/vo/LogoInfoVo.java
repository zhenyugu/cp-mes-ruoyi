package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;


/**
 * logo信息视图对象 logo_info
 *
 * @author cp-mes
 * @date 2023-11-01
 */
@Data
@ExcelIgnoreUnannotated
public class LogoInfoVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * logoID
     */
    @ExcelProperty(value = "logoID")
    private Long id;

    /**
     * 文件ID
     */
    @ExcelProperty(value = "文件ID")
    private Long ossId;

    /**
     * logo类型（0：平台logo，1：浏览器logo）
     */
    @ExcelProperty(value = "logo类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=平台logo，1=浏览器logo")
    private String type;

    /**
     * logo文件url
     */
    private String ossUrl;
}
