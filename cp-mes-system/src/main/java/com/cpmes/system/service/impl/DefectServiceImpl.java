package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.exception.ServiceException;
import com.cpmes.common.utils.DateUtils;
import com.cpmes.common.utils.JsonUtils;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.Task;
import com.cpmes.system.domain.vo.ColumnsVo;
import com.cpmes.system.domain.vo.DefectListVo;
import com.cpmes.system.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.DefectBo;
import com.cpmes.system.domain.vo.DefectVo;
import com.cpmes.system.domain.Defect;
import com.cpmes.system.mapper.DefectMapper;
import com.cpmes.system.service.IDefectService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 不良项Service业务层处理
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@RequiredArgsConstructor
@Service
public class DefectServiceImpl implements IDefectService {

    private final DefectMapper baseMapper;
    private final TaskMapper taskMapper;

    /**
     * 查询不良项
     */
    @Override
    public DefectVo queryById(Long defectId) {
        return baseMapper.selectVoById(defectId);
    }

    /**
     * 查询不良项列表
     */
    @Override
    public TableDataInfo<DefectVo> queryPageList(DefectBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Defect> lqw = buildQueryWrapper(bo);
        Page<DefectVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询不良品项分布
     */
    @Override
    public TableDataInfo<DefectVo> listDefectiveItems(DefectBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Defect> lqw = buildQueryWrapper(bo);
        Page<DefectVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<Task> taskList = taskMapper.selectList(new LambdaQueryWrapper<Task>()
            .isNotNull(Task::getDefectList)
            .isNotNull(Task::getFinishTime)
            .between(bo.getParams().get("reportStart") != null && bo.getParams().get("reportEnd") != null, Task::getFinishTime, bo.getParams().get("reportStart"), bo.getParams().get("reportEnd")));
        // 找出属性 date 的最大值
        Date end = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, taskList.stream()
            .map(Task::getFinishTime)
            .max(Date::compareTo)
            .orElse(new Date())));

        // 找出属性 date 的最小值
        Date start = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, taskList.stream()
            .map(Task::getFinishTime)
            .min(Date::compareTo)
            .orElse(new Date())));
        if (bo.getParams().get("reportStart") != null && bo.getParams().get("reportEnd") != null) {
            start = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtils.parseDate(bo.getParams().get("reportStart"))));
            end = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtils.parseDate(bo.getParams().get("reportEnd"))));
        }
        while (start.before(end) || start.equals(end)) {
            for (DefectVo vo : result.getRecords()) {
                Date finalStart = start;
                List<Task> currentTaskList = taskList.stream().filter(task ->
                    (task.getFinishTime().after(finalStart) || task.getFinishTime().equals(finalStart))
                        && (task.getFinishTime().before(DateUtils.addDays(finalStart, 1)))
                        && task.getDefectList().contains(vo.getDefectName())).collect(Collectors.toList());

                ColumnsVo addColumn = new ColumnsVo();
                addColumn.setTimeLabel(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, start));
                addColumn.setDefectQuantity(getDefectListVo(currentTaskList).stream().filter(d -> d.getDefectId().equals(String.valueOf(vo.getDefectId()))).mapToInt(DefectListVo::getNumber).sum());
                List<ColumnsVo> columnsVos = vo.getColumns();
                if (ObjectUtil.isEmpty(columnsVos)) {
                    columnsVos = new ArrayList<>();
                    List<Task> allCurrentTask = taskList.stream().filter(task -> task.getDefectList().contains(vo.getDefectName())).collect(Collectors.toList());
                    ColumnsVo all = new ColumnsVo();
                    all.setTimeLabel("合计");
                    all.setDefectQuantity(getDefectListVo(allCurrentTask).stream().filter(d -> d.getDefectId().equals(String.valueOf(vo.getDefectId()))).mapToInt(DefectListVo::getNumber).sum());
                    int allNumber = getDefectListVo(taskList).stream().mapToInt(DefectListVo::getNumber).sum();
                    vo.setPercent("0.00%");
                    if (allNumber != 0) {
                        vo.setPercent((BigDecimal.valueOf(all.getDefectQuantity() * 100.0 / allNumber).setScale(2, RoundingMode.HALF_UP)) + "%");
                    }
                    columnsVos.add(all);
                }
                columnsVos.add(addColumn);
                vo.setColumns(columnsVos);
            }
            start = DateUtils.addDays(start, 1);
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询不良项列表
     */
    @Override
    public List<DefectVo> queryList(DefectBo bo) {
        LambdaQueryWrapper<Defect> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Defect> buildQueryWrapper(DefectBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Defect> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getDefectNumber()), Defect::getDefectNumber, bo.getDefectNumber());
        lqw.like(StringUtils.isNotBlank(bo.getDefectName()), Defect::getDefectName, bo.getDefectName());
        return lqw;
    }

    /**
     * 新增不良项
     */
    @Override
    public Boolean insertByBo(DefectBo bo) {
        Defect add = BeanUtil.toBean(bo, Defect.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDefectId(add.getDefectId());
        }
        return flag;
    }

    /**
     * 修改不良项
     */
    @Override
    public Boolean updateByBo(DefectBo bo) {
        Defect update = BeanUtil.toBean(bo, Defect.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Defect entity) {
        //TODO 做一些数据校验,如唯一约束
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<Defect>().eq(Defect::getDefectNumber, entity.getDefectNumber())
            .ne(ObjectUtil.isNotNull(entity.getDefectId()), Defect::getDefectId, entity.getDefectId()));
        if (exist) {
            throw new ServiceException("不良项编号已存在");
        }
    }

    /**
     * 批量删除不良项
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    private List<DefectListVo> getDefectListVo(List<Task> tasks) {
        List<String> defectListAllTotal = new ArrayList<>();
        List<DefectListVo> defectListVosTotal = new ArrayList<>();

        List<String> defectListTotal = tasks.stream().map(Task::getDefectList).collect(Collectors.toList());
        for (String defects : defectListTotal) {
            List<String> realDefects = StringUtils.splitList(defects, "~~");
            defectListAllTotal.addAll(realDefects);
        }
        for (String defect : defectListAllTotal) {
            DefectListVo defectListVo = JsonUtils.parseObject(defect, DefectListVo.class);
            defectListVosTotal.add(defectListVo);
        }
        return defectListVosTotal;

    }
}
