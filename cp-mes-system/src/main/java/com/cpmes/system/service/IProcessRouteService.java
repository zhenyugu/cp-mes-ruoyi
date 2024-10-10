package com.cpmes.system.service;

import com.cpmes.system.domain.ProcessRoute;
import com.cpmes.system.domain.vo.ProcessRouteVo;
import com.cpmes.system.domain.bo.ProcessRouteBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 工艺路线Service接口
 *
 * @author cp-mes
 * @date 2024-01-08
 */
public interface IProcessRouteService {

    /**
     * 查询工艺路线
     */
    ProcessRouteVo queryById(Long processRouteId);

    /**
     * 查询工艺路线列表
     */
    TableDataInfo<ProcessRouteVo> queryPageList(ProcessRouteBo bo, PageQuery pageQuery);

    /**
     * 查询工艺路线列表
     */
    List<ProcessRouteVo> queryList(ProcessRouteBo bo);

    /**
     * 新增工艺路线
     */
    Boolean insertByBo(ProcessRouteBo bo);

    /**
     * 修改工艺路线
     */
    Boolean updateByBo(ProcessRouteBo bo);

    /**
     * 校验并批量删除工艺路线信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
