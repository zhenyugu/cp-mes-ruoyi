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
import com.cpmes.system.domain.bo.ClientBo;
import com.cpmes.system.domain.vo.ClientVo;
import com.cpmes.system.domain.Client;
import com.cpmes.system.mapper.ClientMapper;
import com.cpmes.system.service.IClientService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 客户信息Service业务层处理
 *
 * @author cp-mes
 * @date 2024-03-05
 */
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements IClientService {

    private final ClientMapper baseMapper;

    /**
     * 查询客户信息
     */
    @Override
    public ClientVo queryById(Long clientId){
        return baseMapper.selectVoById(clientId);
    }

    /**
     * 查询客户信息列表
     */
    @Override
    public TableDataInfo<ClientVo> queryPageList(ClientBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Client> lqw = buildQueryWrapper(bo);
        Page<ClientVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户信息列表
     */
    @Override
    public List<ClientVo> queryList(ClientBo bo) {
        LambdaQueryWrapper<Client> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Client> buildQueryWrapper(ClientBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Client> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getClientNumber()), Client::getClientNumber, bo.getClientNumber());
        lqw.like(StringUtils.isNotBlank(bo.getClientName()), Client::getClientName, bo.getClientName());
        lqw.eq(StringUtils.isNotBlank(bo.getClientAbbreviation()), Client::getClientAbbreviation, bo.getClientAbbreviation());
        lqw.eq(StringUtils.isNotBlank(bo.getClientContact()), Client::getClientContact, bo.getClientContact());
        lqw.eq(StringUtils.isNotBlank(bo.getClientPhone()), Client::getClientPhone, bo.getClientPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getClientAvatar()), Client::getClientAvatar, bo.getClientAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), Client::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增客户信息
     */
    @Override
    public Boolean insertByBo(ClientBo bo) {
        Client add = BeanUtil.toBean(bo, Client.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setClientId(add.getClientId());
        }
        return flag;
    }

    /**
     * 修改客户信息
     */
    @Override
    public Boolean updateByBo(ClientBo bo) {
        Client update = BeanUtil.toBean(bo, Client.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Client entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
