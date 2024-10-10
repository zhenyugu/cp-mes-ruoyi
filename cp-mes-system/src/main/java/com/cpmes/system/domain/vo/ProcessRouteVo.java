package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpmes.common.annotation.ExcelDictFormat;
import com.cpmes.common.convert.ExcelDictConvert;
import com.cpmes.common.core.domain.BaseEntity;
import com.cpmes.system.domain.Procedure;
import lombok.Data;

import java.util.List;


/**
 * 工艺路线视图对象 process_route
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Data
@ExcelIgnoreUnannotated
public class ProcessRouteVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long processRouteId;

    /**
     * 工艺路线编号
     */
    @ExcelProperty(value = "工艺路线编号")
    private String processRouteNumber;

    /**
     * 工艺路线名称
     */
    @ExcelProperty(value = "工艺路线名称")
    private String processRouteName;

    /**
     * 工序
     */
    @ExcelProperty(value = "工序")
    private String procedureIds;

    /**
     * 工序名
     */
    @ExcelProperty(value = "工序名")
    private String procedureNames;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    private List<Procedure> procedureList;


}
