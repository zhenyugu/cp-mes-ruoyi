package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.exception.ServiceException;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.Procedure;
import com.cpmes.system.mapper.ProcedureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.ProcessRouteBo;
import com.cpmes.system.domain.vo.ProcessRouteVo;
import com.cpmes.system.domain.ProcessRoute;
import com.cpmes.system.mapper.ProcessRouteMapper;
import com.cpmes.system.service.IProcessRouteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 工艺路线Service业务层处理
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@RequiredArgsConstructor
@Service
public class ProcessRouteServiceImpl implements IProcessRouteService {

    private final ProcessRouteMapper baseMapper;
    private final ProcedureMapper procedureMapper;

    /**
     * 查询工艺路线
     */
    @Override
    public ProcessRouteVo queryById(Long processRouteId) {
        ProcessRouteVo result = baseMapper.selectVoById(processRouteId);
        List<String> procedureIds = StringUtils.splitList(result.getProcedureIds(), ",");
        if (ObjectUtil.isNotEmpty(procedureIds)) {
            List<Procedure> procedures = procedureMapper.selectBatchIds(procedureIds);
            List<Procedure> realProcedures = new ArrayList<>();
            for (int i = 0; i < procedureIds.size(); i++) {
                int finalI = i;
                // 判断工序是否存在再加入工艺路线中
                if (procedures.stream().anyMatch(p -> p.getProcedureId().toString().equals(procedureIds.get(finalI)))) {
                    Procedure procedure = procedures.stream().filter(p -> p.getProcedureId().toString().equals(procedureIds.get(finalI))).findFirst().get();
                    realProcedures.add(i, procedure);
                }
            }
            result.setProcedureList(realProcedures);
        }
        return result;
    }

    /**
     * 查询工艺路线列表
     */
    @Override
    public TableDataInfo<ProcessRouteVo> queryPageList(ProcessRouteBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ProcessRoute> lqw = buildQueryWrapper(bo);
        Page<ProcessRouteVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        for (ProcessRouteVo vo : result.getRecords()) {
            List<String> procedureIds = StringUtils.splitList(vo.getProcedureIds(), ",");
            if (ObjectUtil.isNotEmpty(procedureIds)) {
                List<Procedure> procedures = procedureMapper.selectBatchIds(procedureIds);
                List<Procedure> realProcedures = new ArrayList<>();
                for (int i = 0; i < procedureIds.size(); i++) {
                    // 判断工序是否存在再加入工艺路线中
                    Long procedureId = Long.parseLong(procedureIds.get(i));
                    if (procedures.stream().anyMatch(p -> p.getProcedureId().equals(procedureId))) {
                        Procedure procedure = procedures.stream().filter(p -> p.getProcedureId().equals(procedureId)).collect(Collectors.toList()).get(0);
                        realProcedures.add(i, procedure);
                    }
                }
                vo.setProcedureList(realProcedures);
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询工艺路线列表
     */
    @Override
    public List<ProcessRouteVo> queryList(ProcessRouteBo bo) {
        LambdaQueryWrapper<ProcessRoute> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ProcessRoute> buildQueryWrapper(ProcessRouteBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ProcessRoute> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getProcessRouteNumber()), ProcessRoute::getProcessRouteNumber, bo.getProcessRouteNumber());
        lqw.like(StringUtils.isNotBlank(bo.getProcessRouteName()), ProcessRoute::getProcessRouteName, bo.getProcessRouteName());
        lqw.eq(StringUtils.isNotBlank(bo.getProcedureIds()), ProcessRoute::getProcedureIds, bo.getProcedureIds());
        return lqw;
    }

    /**
     * 新增工艺路线
     */
    @Override
    public Boolean insertByBo(ProcessRouteBo bo) {
        ProcessRoute add = BeanUtil.toBean(bo, ProcessRoute.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setProcessRouteId(add.getProcessRouteId());
        }
        return flag;
    }

    /**
     * 修改工艺路线
     */
    @Override
    public Boolean updateByBo(ProcessRouteBo bo) {
        ProcessRoute update = BeanUtil.toBean(bo, ProcessRoute.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ProcessRoute entity) {
        //TODO 做一些数据校验,如唯一约束
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<ProcessRoute>().eq(ProcessRoute::getProcessRouteNumber, entity.getProcessRouteNumber())
            .ne(ObjectUtil.isNotNull(entity.getProcessRouteId()), ProcessRoute::getProcessRouteId, entity.getProcessRouteId()));
        if (exist) {
            throw new ServiceException("工艺路线编号已存在");
        }
    }

    /**
     * 批量删除工艺路线
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
