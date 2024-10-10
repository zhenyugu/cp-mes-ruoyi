package com.cpmes.system.service;

import com.cpmes.system.domain.Defect;
import com.cpmes.system.domain.vo.DefectVo;
import com.cpmes.system.domain.bo.DefectBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 不良项Service接口
 *
 * @author cp-mes
 * @date 2024-01-08
 */
public interface IDefectService {

    /**
     * 查询不良项
     */
    DefectVo queryById(Long defectId);

    /**
     * 查询不良项列表
     */
    TableDataInfo<DefectVo> queryPageList(DefectBo bo, PageQuery pageQuery);

    /**
     * 查询不良项列表
     */
    TableDataInfo<DefectVo> listDefectiveItems(DefectBo bo, PageQuery pageQuery);

    /**
     * 查询不良项列表
     */
    List<DefectVo> queryList(DefectBo bo);

    /**
     * 新增不良项
     */
    Boolean insertByBo(DefectBo bo);

    /**
     * 修改不良项
     */
    Boolean updateByBo(DefectBo bo);

    /**
     * 校验并批量删除不良项信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
