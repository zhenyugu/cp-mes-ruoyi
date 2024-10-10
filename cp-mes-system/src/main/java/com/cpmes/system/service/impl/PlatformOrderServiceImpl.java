package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.helper.LoginHelper;
import com.cpmes.common.helper.TenantHelper;
import com.cpmes.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.PlatformOrderBo;
import com.cpmes.system.domain.vo.PlatformOrderVo;
import com.cpmes.system.domain.PlatformOrder;
import com.cpmes.system.mapper.PlatformOrderMapper;
import com.cpmes.system.service.IPlatformOrderService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 平台工单信息Service业务层处理
 *
 * @author cp-mes
 * @date 2024-01-31
 */
@RequiredArgsConstructor
@Service
public class PlatformOrderServiceImpl implements IPlatformOrderService {

    private final PlatformOrderMapper baseMapper;

    /**
     * 查询平台工单信息
     */
    @Override
    public PlatformOrderVo queryById(Long platformOrderId){
        return TenantHelper.ignore(() ->baseMapper.selectVoById(platformOrderId));
    }

    /**
     * 查询平台工单信息列表
     */
    @Override
    public TableDataInfo<PlatformOrderVo> queryPageList(PlatformOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PlatformOrder> lqw = buildQueryWrapper(bo);
        Page<PlatformOrderVo> result = TenantHelper.ignore(() -> baseMapper.selectVoPage(pageQuery.build(), lqw));
        return TableDataInfo.build(result);
    }

    /**
     * 查询平台工单信息列表
     */
    @Override
    public List<PlatformOrderVo> queryList(PlatformOrderBo bo) {
        LambdaQueryWrapper<PlatformOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PlatformOrder> buildQueryWrapper(PlatformOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PlatformOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderType()), PlatformOrder::getOrderType, bo.getOrderType());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderContent()), PlatformOrder::getOrderContent, bo.getOrderContent());
        lqw.eq(StringUtils.isNotBlank(bo.getOssIds()), PlatformOrder::getOssIds, bo.getOssIds());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), PlatformOrder::getStatus, bo.getStatus());
        lqw.eq(bo.getUserId() != null, PlatformOrder::getUserId, bo.getUserId());
        return lqw;
    }

    /**
     * 新增平台工单信息
     */
    @Override
    public Boolean insertByBo(PlatformOrderBo bo) {
        PlatformOrder add = BeanUtil.toBean(bo, PlatformOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPlatformOrderId(add.getPlatformOrderId());
        }
        return flag;
    }

    /**
     * 修改平台工单信息
     */
    @Override
    public Boolean updateByBo(PlatformOrderBo bo) {
        PlatformOrder update = BeanUtil.toBean(bo, PlatformOrder.class);
        validEntityBeforeSave(update);
        return TenantHelper.ignore(() ->baseMapper.updateById(update)) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(PlatformOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除平台工单信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
