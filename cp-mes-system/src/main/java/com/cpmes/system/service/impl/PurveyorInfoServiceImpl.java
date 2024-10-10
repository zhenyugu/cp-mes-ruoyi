package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.PurveyorInfoBo;
import com.cpmes.system.domain.vo.PurveyorInfoVo;
import com.cpmes.system.domain.PurveyorInfo;
import com.cpmes.system.mapper.PurveyorInfoMapper;
import com.cpmes.system.service.IPurveyorInfoService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 供应商库管理Service业务层处理
 *
 * @author cp-mes
 * @date 2023-09-11
 */
@RequiredArgsConstructor
@Service
public class PurveyorInfoServiceImpl implements IPurveyorInfoService {

    private final PurveyorInfoMapper baseMapper;

    /**
     * 查询供应商库管理
     */
    @Override
    public PurveyorInfoVo queryById(Long purveyorId){
        return baseMapper.selectVoById(purveyorId);
    }

    /**
     * 查询供应商库管理列表
     */
    @Override
    public TableDataInfo<PurveyorInfoVo> queryPageList(PurveyorInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PurveyorInfo> lqw = buildQueryWrapper(bo);
        Page<PurveyorInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询供应商库管理列表
     */
    @Override
    public List<PurveyorInfoVo> queryList(PurveyorInfoBo bo) {
        LambdaQueryWrapper<PurveyorInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PurveyorInfo> buildQueryWrapper(PurveyorInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PurveyorInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPurveyorCode()), PurveyorInfo::getPurveyorCode, bo.getPurveyorCode());
        lqw.like(StringUtils.isNotBlank(bo.getPurveyorName()), PurveyorInfo::getPurveyorName, bo.getPurveyorName());
        lqw.like(StringUtils.isNotBlank(bo.getNature()), PurveyorInfo::getNature, bo.getNature());
        lqw.like(StringUtils.isNotBlank(bo.getSupplyType()), PurveyorInfo::getSupplyType, bo.getSupplyType());
        lqw.like(StringUtils.isNotBlank(bo.getContractName()), PurveyorInfo::getContractName, bo.getContractName());
        lqw.eq(StringUtils.isNotBlank(bo.getContractPost()), PurveyorInfo::getContractPost, bo.getContractPost());
        lqw.eq(StringUtils.isNotBlank(bo.getContractPhone()), PurveyorInfo::getContractPhone, bo.getContractPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getContractEmail()), PurveyorInfo::getContractEmail, bo.getContractEmail());
        lqw.eq(StringUtils.isNotBlank(bo.getAddress()), PurveyorInfo::getAddress, bo.getAddress());
        lqw.orderByDesc(PurveyorInfo::getCreateTime);
        return lqw;
    }

    /**
     * 新增供应商库管理
     */
    @Override
    public Boolean insertByBo(PurveyorInfoBo bo) {
        PurveyorInfo add = BeanUtil.toBean(bo, PurveyorInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPurveyorId(add.getPurveyorId());
        }
        return flag;
    }

    /**
     * 修改供应商库管理
     */
    @Override
    public Boolean updateByBo(PurveyorInfoBo bo) {
        PurveyorInfo update = BeanUtil.toBean(bo, PurveyorInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(PurveyorInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除供应商库管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
