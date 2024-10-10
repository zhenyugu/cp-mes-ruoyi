package com.cpmes.system.service;

import com.cpmes.system.domain.Client;
import com.cpmes.system.domain.vo.ClientVo;
import com.cpmes.system.domain.bo.ClientBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 客户信息Service接口
 *
 * @author cp-mes
 * @date 2024-03-05
 */
public interface IClientService {

    /**
     * 查询客户信息
     */
    ClientVo queryById(Long clientId);

    /**
     * 查询客户信息列表
     */
    TableDataInfo<ClientVo> queryPageList(ClientBo bo, PageQuery pageQuery);

    /**
     * 查询客户信息列表
     */
    List<ClientVo> queryList(ClientBo bo);

    /**
     * 新增客户信息
     */
    Boolean insertByBo(ClientBo bo);

    /**
     * 修改客户信息
     */
    Boolean updateByBo(ClientBo bo);

    /**
     * 校验并批量删除客户信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
