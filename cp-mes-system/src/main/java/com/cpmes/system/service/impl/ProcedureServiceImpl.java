package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.exception.ServiceException;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.Defect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.ProcedureBo;
import com.cpmes.system.domain.vo.ProcedureVo;
import com.cpmes.system.domain.Procedure;
import com.cpmes.system.mapper.ProcedureMapper;
import com.cpmes.system.service.IProcedureService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 工序Service业务层处理
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@RequiredArgsConstructor
@Service
public class ProcedureServiceImpl implements IProcedureService {

    private final ProcedureMapper baseMapper;

    /**
     * 查询工序
     */
    @Override
    public ProcedureVo queryById(Long procedureId){
        return baseMapper.selectVoById(procedureId);
    }

    /**
     * 查询工序列表
     */
    @Override
    public TableDataInfo<ProcedureVo> queryPageList(ProcedureBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Procedure> lqw = buildQueryWrapper(bo);
        Page<ProcedureVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询工序列表
     */
    @Override
    public List<ProcedureVo> queryList(ProcedureBo bo) {
        LambdaQueryWrapper<Procedure> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Procedure> buildQueryWrapper(ProcedureBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Procedure> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getProcedureNumber()), Procedure::getProcedureNumber, bo.getProcedureNumber());
        lqw.like(StringUtils.isNotBlank(bo.getProcedureName()), Procedure::getProcedureName, bo.getProcedureName());
        lqw.eq(StringUtils.isNotBlank(bo.getReportingAuthority()), Procedure::getReportingAuthority, bo.getReportingAuthority());
        lqw.eq(StringUtils.isNotBlank(bo.getPlannedQuantity()), Procedure::getPlannedQuantity, bo.getPlannedQuantity());
        lqw.eq(bo.getReportingRatio() != null, Procedure::getReportingRatio, bo.getReportingRatio());
        lqw.eq(StringUtils.isNotBlank(bo.getDefectiveProducts()), Procedure::getDefectiveProducts, bo.getDefectiveProducts());
        lqw.eq(StringUtils.isNotBlank(bo.getCheckIn()), Procedure::getCheckIn, bo.getCheckIn());
        lqw.eq(StringUtils.isNotBlank(bo.getTechnologicalRequirements()), Procedure::getTechnologicalRequirements, bo.getTechnologicalRequirements());
        lqw.eq(StringUtils.isNotBlank(bo.getIsPrint()), Procedure::getIsPrint, bo.getIsPrint());
        lqw.eq(StringUtils.isNotBlank(bo.getCollectionData()), Procedure::getCollectionData, bo.getCollectionData());
        return lqw;
    }

    /**
     * 新增工序
     */
    @Override
    public Boolean insertByBo(ProcedureBo bo) {
        Procedure add = BeanUtil.toBean(bo, Procedure.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setProcedureId(add.getProcedureId());
        }
        return flag;
    }

    /**
     * 修改工序
     */
    @Override
    public Boolean updateByBo(ProcedureBo bo) {
        Procedure update = BeanUtil.toBean(bo, Procedure.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Procedure entity){
        //TODO 做一些数据校验,如唯一约束
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<Procedure>().eq(Procedure::getProcedureNumber,entity.getProcedureNumber())
            .ne(ObjectUtil.isNotNull(entity.getProcedureId()),Procedure::getProcedureId, entity.getProcedureId()));
        if (exist){
            throw new ServiceException("工序编号已存在");
        }
    }

    /**
     * 批量删除工序
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
