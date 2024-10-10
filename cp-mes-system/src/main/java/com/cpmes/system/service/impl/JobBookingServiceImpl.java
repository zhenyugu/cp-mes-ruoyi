package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.utils.JsonUtils;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.Defect;
import com.cpmes.system.domain.Task;
import com.cpmes.system.domain.vo.ColumnsVo;
import com.cpmes.system.domain.vo.DefectListVo;
import com.cpmes.system.domain.vo.DefectVo;
import com.cpmes.system.mapper.DefectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.JobBookingBo;
import com.cpmes.system.domain.vo.JobBookingVo;
import com.cpmes.system.domain.JobBooking;
import com.cpmes.system.mapper.JobBookingMapper;
import com.cpmes.system.service.IJobBookingService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 报工Service业务层处理
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@RequiredArgsConstructor
@Service
public class JobBookingServiceImpl implements IJobBookingService {

    private final JobBookingMapper baseMapper;
    private final DefectMapper defectMapper;

    /**
     * 查询报工
     */
    @Override
    public JobBookingVo queryById(Long jobBookingId){
        return baseMapper.selectVoById(jobBookingId);
    }

    /**
     * 查询报工列表
     */
    @Override
    public TableDataInfo<JobBookingVo> queryPageList(JobBookingBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<JobBooking> lqw = buildQueryWrapper(bo);
        Page<JobBookingVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        for (JobBookingVo vo : result.getRecords()){
            vo.setPercent((BigDecimal.valueOf(vo.getDefectQuantity()*100.0/vo.getJobBookingNum()).setScale(2, RoundingMode.HALF_UP))+"%");
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询不良品项明细
     */
    @Override
    public TableDataInfo<JobBookingVo> defectDetails(JobBookingBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<JobBooking> lqw = buildQueryWrapper(bo);
        Page<JobBookingVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<Defect> defectList = defectMapper.selectList();
        for (JobBookingVo vo : result.getRecords()){
            List<ColumnsVo> columnsVos = new ArrayList<>();
            List<DefectListVo> defectListVosTotal = new ArrayList<>();
            List<String> realDefects = StringUtils.splitList(vo.getDefectiveNames(), "~~");
            for (String defect : realDefects) {
                DefectListVo defectListVo = JsonUtils.parseObject(defect, DefectListVo.class);
                defectListVosTotal.add(defectListVo);
            }
            for (Defect defect : defectList){
                ColumnsVo columnsVo = new ColumnsVo();
                DefectListVo findDefect = defectListVosTotal.stream().filter(d -> d.getDefectName().equals(defect.getDefectName())).findFirst().orElse(null);
                columnsVo.setTimeLabel(defect.getDefectName());
                if (ObjectUtil.isNotEmpty(findDefect)){
                    columnsVo.setDefectQuantity(findDefect.getNumber());
                    columnsVo.setPercent(BigDecimal.valueOf(findDefect.getNumber() * 100 / vo.getJobBookingNum()) + "%");
                }
                columnsVos.add(columnsVo);
            }
            vo.setColumns(columnsVos);
            vo.setPercent((BigDecimal.valueOf(vo.getDefectQuantity()*100.0/vo.getJobBookingNum()).setScale(2, RoundingMode.HALF_UP))+"%");
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询报工列表
     */
    @Override
    public List<JobBookingVo> queryList(JobBookingBo bo) {
        LambdaQueryWrapper<JobBooking> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<JobBooking> buildQueryWrapper(JobBookingBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<JobBooking> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getSheetNumber()), JobBooking::getSheetNumber, bo.getSheetNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getProcedureNumber()), JobBooking::getProcedureNumber, bo.getProcedureNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getProductNumber()), JobBooking::getProductNumber, bo.getProductNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getProcedureName()), JobBooking::getProcedureName, bo.getProcedureName());
        lqw.eq(StringUtils.isNotBlank(bo.getProcedureStatus()), JobBooking::getProcedureStatus, bo.getProcedureStatus());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), JobBooking::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getProductSpecification()), JobBooking::getProductSpecification, bo.getProductSpecification());
        lqw.eq(bo.getJobBookingNum() != null, JobBooking::getJobBookingNum, bo.getJobBookingNum());
        lqw.eq(StringUtils.isNotBlank(bo.getPricingMethod()), JobBooking::getPricingMethod, bo.getPricingMethod());
        lqw.eq(bo.getUnitPrice() != null, JobBooking::getUnitPrice, bo.getUnitPrice());
        lqw.eq(bo.getEstimatedSalary() != null, JobBooking::getEstimatedSalary, bo.getEstimatedSalary());
        lqw.eq(bo.getGoodQuantity() != null, JobBooking::getGoodQuantity, bo.getGoodQuantity());
        lqw.eq(bo.getDefectQuantity() != null, JobBooking::getDefectQuantity, bo.getDefectQuantity());
        lqw.eq(StringUtils.isNotBlank(bo.getUnit()), JobBooking::getUnit, bo.getUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getDefectId()), JobBooking::getDefectId, bo.getDefectId());
        lqw.eq(StringUtils.isNotBlank(bo.getProductionPersonnel()), JobBooking::getProductionPersonnel, bo.getProductionPersonnel());
        lqw.eq(bo.getStartTime() != null, JobBooking::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, JobBooking::getEndTime, bo.getEndTime());
        lqw.eq(StringUtils.isNotBlank(bo.getJobBookingCount()), JobBooking::getJobBookingCount, bo.getJobBookingCount());
        lqw.between(bo.getParams().get("startTime") != null && bo.getParams().get("endTime") != null,JobBooking::getCreateTime,bo.getParams().get("startTime"),bo.getParams().get("endTime"));
        lqw.orderByDesc(JobBooking::getCreateTime);
        return lqw;
    }

    /**
     * 新增报工
     */
    @Override
    public Boolean insertByBo(JobBookingBo bo) {
        JobBooking add = BeanUtil.toBean(bo, JobBooking.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setJobBookingId(add.getJobBookingId());
        }
        return flag;
    }

    /**
     * 修改报工
     */
    @Override
    public Boolean updateByBo(JobBookingBo bo) {
        JobBooking update = BeanUtil.toBean(bo, JobBooking.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(JobBooking entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除报工
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
