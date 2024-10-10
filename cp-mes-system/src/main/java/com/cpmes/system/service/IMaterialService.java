package com.cpmes.system.service;

import com.cpmes.system.domain.Material;
import com.cpmes.system.domain.vo.MaterialVo;
import com.cpmes.system.domain.bo.MaterialBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 物料Service接口
 *
 * @author cp-mes
 * @date 2024-03-14
 */
public interface IMaterialService {

    /**
     * 查询物料
     */
    MaterialVo queryById(Long materialId);

    /**
     * 查询物料列表
     */
    TableDataInfo<MaterialVo> queryPageList(MaterialBo bo, PageQuery pageQuery);

    /**
     * 查询物料列表
     */
    List<MaterialVo> queryList(MaterialBo bo);

    /**
     * 新增物料
     */
    Boolean insertByBo(MaterialBo bo);

    /**
     * 修改物料
     */
    Boolean updateByBo(MaterialBo bo);

    /**
     * 校验并批量删除物料信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
