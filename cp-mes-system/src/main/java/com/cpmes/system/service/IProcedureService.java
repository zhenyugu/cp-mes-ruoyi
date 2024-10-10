package com.cpmes.system.service;

import com.cpmes.system.domain.Procedure;
import com.cpmes.system.domain.vo.ProcedureVo;
import com.cpmes.system.domain.bo.ProcedureBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 工序Service接口
 *
 * @author cp-mes
 * @date 2024-01-08
 */
public interface IProcedureService {

    /**
     * 查询工序
     */
    ProcedureVo queryById(Long procedureId);

    /**
     * 查询工序列表
     */
    TableDataInfo<ProcedureVo> queryPageList(ProcedureBo bo, PageQuery pageQuery);

    /**
     * 查询工序列表
     */
    List<ProcedureVo> queryList(ProcedureBo bo);

    /**
     * 新增工序
     */
    Boolean insertByBo(ProcedureBo bo);

    /**
     * 修改工序
     */
    Boolean updateByBo(ProcedureBo bo);

    /**
     * 校验并批量删除工序信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
