package com.cpmes.system.service;

import com.cpmes.system.domain.bo.LogoInfoBo;
import com.cpmes.system.domain.vo.LogoInfoVo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * logo信息Service接口
 *
 * @author cp-mes
 * @date 2023-11-01
 */
public interface ILogoInfoService {

    /**
     * 查询logo信息
     */
    LogoInfoVo queryById(Long id);

    /**
     * 查询logo信息列表
     */
    TableDataInfo<LogoInfoVo> queryPageList(LogoInfoBo bo, PageQuery pageQuery);

    /**
     * 查询logo信息列表
     */
    List<LogoInfoVo> queryList(LogoInfoBo bo);

    /**
     * 新增logo信息
     */
    Boolean insertByBo(LogoInfoBo bo);

    /**
     * 修改logo信息
     */
    Boolean updateByBo(LogoInfoBo bo);

    /**
     * 校验并批量删除logo信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
