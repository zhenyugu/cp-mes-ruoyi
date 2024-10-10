package com.cpmes.system.service;

import com.cpmes.system.domain.SheetMaterial;
import com.cpmes.system.domain.vo.SheetMaterialVo;
import com.cpmes.system.domain.bo.SheetMaterialBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 工单物料Service接口
 *
 * @author cp-mes
 * @date 2024-03-29
 */
public interface ISheetMaterialService {

    /**
     * 查询工单物料
     */
    SheetMaterialVo queryById(Long attachmentId);

    /**
     * 查询工单物料列表
     */
    TableDataInfo<SheetMaterialVo> queryPageList(SheetMaterialBo bo, PageQuery pageQuery);

    /**
     * 查询工单物料列表
     */
    List<SheetMaterialVo> queryList(SheetMaterialBo bo);

    /**
     * 新增工单物料
     */
    Boolean insertByBo(SheetMaterialBo bo);

    /**
     * 修改工单物料
     */
    Boolean updateByBo(SheetMaterialBo bo);

    /**
     * 校验并批量删除工单物料信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
