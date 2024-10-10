package com.cpmes.system.service;

import com.cpmes.system.domain.vo.PlatformOrderVo;
import com.cpmes.system.domain.bo.PlatformOrderBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 平台工单信息Service接口
 *
 * @author cp-mes
 * @date 2024-01-31
 */
public interface IPlatformOrderService {

    /**
     * 查询平台工单信息
     */
    PlatformOrderVo queryById(Long platformOrderId);

    /**
     * 查询平台工单信息列表
     */
    TableDataInfo<PlatformOrderVo> queryPageList(PlatformOrderBo bo, PageQuery pageQuery);

    /**
     * 查询平台工单信息列表
     */
    List<PlatformOrderVo> queryList(PlatformOrderBo bo);

    /**
     * 新增平台工单信息
     */
    Boolean insertByBo(PlatformOrderBo bo);

    /**
     * 修改平台工单信息
     */
    Boolean updateByBo(PlatformOrderBo bo);

    /**
     * 校验并批量删除平台工单信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
