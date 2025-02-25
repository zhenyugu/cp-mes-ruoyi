package com.cpmes.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 自定义报相关字段对象 field
 *
 * @author cp-mes
 * @date 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("field")
public class Field extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 编号
     */
    @TableId(value = "column_id")
    private Long columnId;
    /**
     * 归属表编号
     */
    private Long tableId;
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 列描述
     */
    private String columnComment;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * JAVA类型
     */
    private String javaType;
    /**
     * JAVA字段名
     */
    private String javaField;
    /**
     * 是否主键（1是）
     */
    private String isPk;
    /**
     * 是否自增（1是）
     */
    private String isIncrement;
    /**
     * 是否必填（1是）
     */
    private String isRequired;
    /**
     * 是否为插入字段（1是）
     */
    private String isInsert;
    /**
     * 是否编辑字段（1是）
     */
    private String isEdit;
    /**
     * 是否列表字段（1是）
     */
    private String isList;
    /**
     * 是否查询字段（1是）
     */
    private String isQuery;
    /**
     * 查询方式（等于、不等于、大于、小于、范围）
     */
    private String queryType;
    /**
     * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
     */
    private String htmlType;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 排序
     */
    private Long sort;

}
