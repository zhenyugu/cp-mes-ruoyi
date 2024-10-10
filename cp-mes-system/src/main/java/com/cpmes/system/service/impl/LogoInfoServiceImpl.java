package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.helper.TenantHelper;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.LogoInfo;
import com.cpmes.system.domain.SysOss;
import com.cpmes.system.domain.bo.LogoInfoBo;
import com.cpmes.system.domain.vo.LogoInfoVo;
import com.cpmes.system.mapper.LogoInfoMapper;
import com.cpmes.system.mapper.SysOssMapper;
import com.cpmes.system.service.ILogoInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * logo信息Service业务层处理
 *
 * @author cp-mes
 * @date 2023-11-01
 */
@RequiredArgsConstructor
@Service
public class LogoInfoServiceImpl implements ILogoInfoService {

    private final LogoInfoMapper baseMapper;
    private final SysOssMapper sysOssMapper;

    /**
     * 查询logo信息
     */
    @Override
    public LogoInfoVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询logo信息列表
     */
    @Override
    public TableDataInfo<LogoInfoVo> queryPageList(LogoInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LogoInfo> lqw = buildQueryWrapper(bo);
        Page<LogoInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<LogoInfoVo> logoList = result.getRecords();
        List<Long> ids = logoList.stream().map(l -> l.getOssId()).collect(Collectors.toList());
        List<SysOss> ossList = TenantHelper.ignore(() ->sysOssMapper.selectBatchIds(ids));
        for (LogoInfoVo logo : logoList) {
            Optional<SysOss> oss = ossList.stream().filter(o -> o.getOssId().equals(logo.getOssId())).findAny();
            if(ObjectUtil.isNotNull(oss)) {
                logo.setOssUrl(oss.get().getUrl());
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询logo信息列表
     */
    @Override
    public List<LogoInfoVo> queryList(LogoInfoBo bo) {
        LambdaQueryWrapper<LogoInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LogoInfo> buildQueryWrapper(LogoInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LogoInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOssId() != null, LogoInfo::getOssId, bo.getOssId());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), LogoInfo::getType, bo.getType());
        return lqw;
    }

    /**
     * 新增logo信息
     */
    @Override
    public Boolean insertByBo(LogoInfoBo bo) {
        LogoInfo add = BeanUtil.toBean(bo, LogoInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改logo信息
     */
    @Override
    public Boolean updateByBo(LogoInfoBo bo) {
        LogoInfo update = BeanUtil.toBean(bo, LogoInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LogoInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除logo信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
