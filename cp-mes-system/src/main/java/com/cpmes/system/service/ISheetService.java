package com.cpmes.system.service;

import com.cpmes.system.domain.Sheet;
import com.cpmes.system.domain.vo.SheetVo;
import com.cpmes.system.domain.bo.SheetBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 工单Service接口
 *
 * @author cp-mes
 * @date 2024-01-11
 */
public interface ISheetService {

    /**
     * 查询工单
     */
    SheetVo queryById(Long sheetId);

    /**
     * 查询工单(移动端)
     */
    SheetVo queryByIdMobile(Long sheetId);

    /**
     * 查询工单
     */
    SheetVo queryBySheetNumber(String SheetNumber);

    /**
     * 查询工单列表
     */
    TableDataInfo<SheetVo> queryPageList(SheetBo bo, PageQuery pageQuery);

    /**
     * 查询工单列表
     */
    List<SheetVo> queryList(SheetBo bo);

    /**
     * 新增工单
     */
    Boolean insertByBo(SheetBo bo);

    /**
     * 修改工单
     */
    Boolean updateByBo(SheetBo bo);

    /**
     * 校验并批量删除工单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
