package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.helper.LoginHelper;
import com.cpmes.common.utils.DateUtils;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.JobBooking;
import com.cpmes.system.domain.Sheet;
import com.cpmes.system.domain.bo.SheetBo;
import com.cpmes.system.domain.vo.JobBookingVo;
import com.cpmes.system.domain.vo.SheetVo;
import com.cpmes.system.mapper.JobBookingMapper;
import com.cpmes.system.mapper.SheetMapper;
import com.cpmes.system.service.ISheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.TaskBo;
import com.cpmes.system.domain.vo.TaskVo;
import com.cpmes.system.domain.Task;
import com.cpmes.system.mapper.TaskMapper;
import com.cpmes.system.service.ITaskService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 任务Service业务层处理
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements ITaskService {

    private final TaskMapper baseMapper;
    private final SheetMapper sheetMapper;
    private final JobBookingMapper jobBookingMapper;
    private final ISheetService sheetService;

    /**
     * 查询任务
     */
    @Override
    public TaskVo queryById(Long taskId) {
        return baseMapper.selectVoById(taskId);
    }

    /**
     * 查询任务列表
     */
    @Override
    public TableDataInfo<TaskVo> queryPageList(TaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Task> lqw = buildQueryWrapper(bo);
        Page<TaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询任务列表
     */
    @Override
    public List<TaskVo> queryList(TaskBo bo) {
        LambdaQueryWrapper<Task> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Task> buildQueryWrapper(TaskBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Task> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getSheetId()), Task::getSheetId, bo.getSheetId());
        lqw.eq(StringUtils.isNotBlank(bo.getSheetNumber()), Task::getSheetNumber, bo.getSheetNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getProductNumber()), Task::getProductNumber, bo.getProductNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getProductName()), Task::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getProductSpecification()), Task::getProductSpecification, bo.getProductSpecification());
        lqw.eq(StringUtils.isNotBlank(bo.getProcedureId()), Task::getProcedureId, bo.getProcedureId());
        lqw.eq(StringUtils.isNotBlank(bo.getProcedureNumber()), Task::getProcedureNumber, bo.getProcedureNumber());
        lqw.like(StringUtils.isNotBlank(bo.getProcedureName()), Task::getProcedureName, bo.getProcedureName());
        lqw.like(StringUtils.isNotBlank(bo.getReportingAuthority()), Task::getReportingAuthority, bo.getReportingAuthority());
        lqw.like(StringUtils.isNotBlank(bo.getReportingName()), Task::getReportingName, bo.getReportingName());
        lqw.eq(StringUtils.isNotBlank(bo.getDistributionList()), Task::getDistributionList, bo.getDistributionList());
        lqw.eq(bo.getReportingRatio() != null, Task::getReportingRatio, bo.getReportingRatio());
        lqw.eq(StringUtils.isNotBlank(bo.getProcedureStatus()), Task::getProcedureStatus, bo.getProcedureStatus());
        lqw.eq(bo.getPlannedQuantity() != null, Task::getPlannedQuantity, bo.getPlannedQuantity());
        lqw.eq(bo.getGoodQuantity() != null, Task::getGoodQuantity, bo.getGoodQuantity());
        lqw.eq(bo.getDefectQuantity() != null, Task::getDefectQuantity, bo.getDefectQuantity());
        lqw.eq(StringUtils.isNotBlank(bo.getDefectiveProducts()), Task::getDefectiveProducts, bo.getDefectiveProducts());
        lqw.eq(StringUtils.isNotBlank(bo.getDefectiveNames()), Task::getDefectiveNames, bo.getDefectiveNames());
        lqw.eq(bo.getStartTimePlan() != null, Task::getStartTimePlan, bo.getStartTimePlan());
        lqw.eq(bo.getEndTimePlan() != null, Task::getEndTimePlan, bo.getEndTimePlan());
        lqw.eq(bo.getStartTime() != null, Task::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, Task::getEndTime, bo.getEndTime());
        lqw.between(bo.getParams().get("startTime") != null && bo.getParams().get("endTime") != null, Task::getCreateTime, bo.getParams().get("startTime"), bo.getParams().get("endTime"));
        lqw.orderByDesc(Task::getCreateTime);
        return lqw;
    }

    /**
     * 新增任务
     */
    @Override
    public Boolean insertByBo(TaskBo bo) {
        Task add = BeanUtil.toBean(bo, Task.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setTaskId(add.getTaskId());
        }
        return flag;
    }

    /**
     * 修改任务
     */
    @Override
    public Boolean updateByBo(TaskBo bo) {
        Task update = BeanUtil.toBean(bo, Task.class);
        if (update.getProcedureStatus().equals("2")) {
            update.setFinishBy(Convert.toStr(LoginHelper.getUserId()));
            update.setFinishName(LoginHelper.getUsername());
            update.setFinishTime(new Date());
        }
        validEntityBeforeSave(update);
        if (baseMapper.updateById(update) > 0) {
            List<Task> tasks = baseMapper.selectList(new LambdaQueryWrapper<Task>().eq(Task::getSheetId, bo.getSheetId()));
            boolean isFinish = tasks.stream().allMatch(t -> t.getProcedureStatus().equals("2"));
            boolean isLoading = tasks.stream().anyMatch(t -> t.getProcedureStatus().equals("1") || t.getProcedureStatus().equals("2"));
            Sheet sheet = sheetMapper.selectById(update.getSheetId());
            Task latest = Objects.requireNonNull(tasks.stream().sorted(Comparator.comparing(Task::getUpdateTime).reversed()).findFirst().orElse(null));
            sheet.setGoodQuantity(latest.getGoodQuantity());
            sheet.setDefectQuantity(latest.getDefectQuantity());
            if (isLoading) {
                sheet.setStatus("1");
            }
            if (isFinish) {
                sheet.setStatus("2");
            }
            return sheetMapper.updateById(sheet) > 0;
        }
        return false;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Task entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除任务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 数据概览界面charts
     */
    @Override
    public Map<String, Object> overviewCharts(TaskBo bo) {
        Map<String, Object> result = new HashMap<>();
        List<TaskVo> taskVos = queryList(bo);
        List<Sheet> sheets = sheetMapper.selectList(new LambdaQueryWrapper<Sheet>().between(bo.getParams().get("startTime") != null && bo.getParams().get("endTime") != null, Sheet::getPlanStart, bo.getParams().get("startTime"), bo.getParams().get("endTime")));
        Date start = DateUtils.parseDate(bo.getParams().get("startTime"));
        Date end = DateUtils.parseDate(bo.getParams().get("endTime"));
        List<TaskVo> planAndOut = new ArrayList<>();
        List<TaskVo> taskStatus = new ArrayList<>();
        List<TaskVo> taskNames = new ArrayList<>();
        while (start.before(end)) {
            TaskVo planAndOutVo = new TaskVo();
            TaskVo taskStatusVo = new TaskVo();

            Date finalStart = start;
            List<Sheet> currentSheet = sheets.stream().filter(e -> (e.getPlanStart().after(finalStart) || e.getPlanStart().equals(finalStart)) && (e.getPlanStart().before(DateUtils.addDays(finalStart, 1)))).collect(Collectors.toList());
            List<String> sheetIds = currentSheet.stream().map(sheet -> String.valueOf(sheet.getSheetId())).collect(Collectors.toList());
            String sheetIdsStr = StringUtils.join(sheetIds, ",");
            List<TaskVo> current = taskVos.stream().filter(e -> sheetIdsStr.contains(e.getSheetId())).collect(Collectors.toList());

            Map<String, List<TaskVo>> groupedEvents = current.stream()
                .collect(Collectors.groupingBy(TaskVo::getSheetId));

            // 对每个分组按照 time 属性倒序排序并取第一个元素
            List<TaskVo> currentDistinct = groupedEvents.values().stream()
                .map(group -> group.stream()
                    .sorted(Comparator.comparing(TaskVo::getUpdateTime).reversed())
                    .findFirst().orElse(null))
                .collect(Collectors.toList());

            planAndOutVo.setPlannedQuantity(currentSheet.stream().filter(t -> ObjectUtil.isNotEmpty(t.getPlannedQuantity())).mapToInt(Sheet::getPlannedQuantity).sum());
            planAndOutVo.setGoodQuantity(currentDistinct.stream().filter(t -> ObjectUtil.isNotEmpty(t.getGoodQuantity())).mapToInt(TaskVo::getGoodQuantity).sum());
            planAndOutVo.setDefectQuantity(currentDistinct.stream().filter(t -> ObjectUtil.isNotEmpty(t.getDefectQuantity())).mapToInt(TaskVo::getDefectQuantity).sum());
            planAndOutVo.setDateTime(DateUtils.parseDateToStr("MM-dd", start));
            planAndOut.add(planAndOutVo);

            taskStatusVo.setNotStart(current.stream().filter(e -> e.getProcedureStatus().equals("0")).count());
            taskStatusVo.setLoading(current.stream().filter(e -> e.getProcedureStatus().equals("1")).count());
            taskStatusVo.setFinish(current.stream().filter(e -> e.getProcedureStatus().equals("2")).count());
            taskStatusVo.setDateTime(DateUtils.parseDateToStr("MM-dd", start));
            taskStatus.add(taskStatusVo);
            start = DateUtils.addDays(start, 1);
        }
        Map<String, Long> taskNameMap;
        taskNameMap = taskVos.stream().filter(p -> StringUtils.isNotBlank(p.getProcedureName())).collect(Collectors.groupingBy(TaskVo::getProcedureName, Collectors.counting()));
        //遍历所有任务信息存入list
        for (String taskName : taskNameMap.keySet()) {
            TaskVo taskNameVo = new TaskVo();
            taskNameVo.setName(taskName);
            taskNameVo.setValue(taskNameMap.get(taskName));
            taskNames.add(taskNameVo);
        }
        int good = taskVos.stream().filter(t -> ObjectUtil.isNotEmpty(t.getGoodQuantity())).mapToInt(TaskVo::getGoodQuantity).sum();
        int bad = taskVos.stream().filter(t -> ObjectUtil.isNotEmpty(t.getDefectQuantity())).mapToInt(TaskVo::getDefectQuantity).sum();
        result.put("planAndOut", planAndOut);
        result.put("taskStatus", taskStatus);
        result.put("taskNames", taskNames);
        result.put("defectYield", BigDecimal.ZERO);
        if (good + bad != 0) {
            result.put("defectYield", BigDecimal.valueOf(bad).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(good + bad), 2, RoundingMode.HALF_UP));
        }
        return result;
    }
    /**
     * 数据看板接口
     */
    public Map<String,Object> dataDashboards(){
        Map<String, Object> result = new HashMap<>();
        Date now = DateUtils.getNowDate();
        List<Sheet> sheets = sheetMapper.selectList();
        int good = sheets.stream().filter(t -> ObjectUtil.isNotEmpty(t.getGoodQuantity())).mapToInt(Sheet::getGoodQuantity).sum();
        int bad = sheets.stream().filter(t -> ObjectUtil.isNotEmpty(t.getDefectQuantity())).mapToInt(Sheet::getDefectQuantity).sum();
        result.put("total",sheets.size());
        result.put("totalPlan",sheets.stream().filter(t -> ObjectUtil.isNotEmpty(t.getPlannedQuantity())).mapToInt(Sheet::getPlannedQuantity).sum());
        result.put("finish",sheets.stream().filter(t -> t.getStatus().equals("2")).count());
        result.put("delay",sheets.stream().filter(t -> t.getPlanEnd().after(now)).count());
        result.put("yield", BigDecimal.valueOf(100).setScale(0,RoundingMode.HALF_UP));
        if (good + bad != 0) {
            result.put("yield", BigDecimal.valueOf(good).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(good + bad), 2, RoundingMode.HALF_UP));
        }
        List<TaskVo> planAndOut = new ArrayList<>();
        Date start = DateUtils.addMonths(now,-1);
        Date end = now;
        while (start.before(end)) {
            TaskVo planAndOutVo = new TaskVo();
            Date finalStart = start;
            List<Sheet> currentSheet = sheets.stream().filter(e -> (e.getPlanStart().after(finalStart) || e.getPlanStart().equals(finalStart)) && (e.getPlanStart().before(DateUtils.addDays(finalStart, 1)))).collect(Collectors.toList());
            planAndOutVo.setPlannedQuantity(currentSheet.stream().filter(t -> ObjectUtil.isNotEmpty(t.getPlannedQuantity())).mapToInt(Sheet::getPlannedQuantity).sum());
            planAndOutVo.setGoodQuantity(currentSheet.stream().filter(t -> ObjectUtil.isNotEmpty(t.getGoodQuantity())).mapToInt(Sheet::getGoodQuantity).sum());
            planAndOutVo.setDefectQuantity(currentSheet.stream().filter(t -> ObjectUtil.isNotEmpty(t.getDefectQuantity())).mapToInt(Sheet::getDefectQuantity).sum());
            planAndOutVo.setDateTime(DateUtils.parseDateToStr("MM-dd", start));
            planAndOut.add(planAndOutVo);
            start = DateUtils.addDays(start, 1);
        }
        result.put("planAndOut", planAndOut);

        List<TaskVo> taskProgress = new ArrayList<>();
        List<TaskVo> taskVos = queryList(new TaskBo());
        Map<String, List<TaskVo>> groupedByTaskName = taskVos.stream()
            .collect(Collectors.groupingBy(TaskVo::getProcedureName));

        groupedByTaskName.forEach((taskName, group) -> {
            TaskVo taskVo = new TaskVo();
            taskVo.setProcedureName(taskName);
            taskVo.setValue((long) group.size());
            taskVo.setPlannedQuantity(group.stream().filter(t -> ObjectUtil.isNotEmpty(t.getPlannedQuantity())).mapToInt(TaskVo::getPlannedQuantity).sum());
            taskVo.setGoodQuantity(group.stream().filter(t -> ObjectUtil.isNotEmpty(t.getGoodQuantity())).mapToInt(TaskVo::getGoodQuantity).sum());
            taskVo.setDefectQuantity(group.stream().filter(t -> ObjectUtil.isNotEmpty(t.getDefectQuantity())).mapToInt(TaskVo::getDefectQuantity).sum());
            taskVo.setUnfinished(group.stream().filter(t -> !t.getProcedureStatus().equals("2")).count());
            taskProgress.add(taskVo);
        });
        result.put("taskProgress", taskProgress);

        List<SheetVo> sheetPlan = new ArrayList<>();
        Map<String, List<Sheet>> groupedByProductName = sheets.stream()
            .collect(Collectors.groupingBy(Sheet::getProductName));

        groupedByProductName.forEach((productName, group) -> {
            SheetVo sheet = new SheetVo();
            sheet.setProductName(productName);
            sheet.setPlannedQuantity(group.stream().filter(t -> ObjectUtil.isNotEmpty(t.getPlannedQuantity())).mapToInt(Sheet::getPlannedQuantity).sum());
            sheetPlan.add(sheet);
        });
        result.put("sheetPlan", sheetPlan.stream().sorted(Comparator.comparing(SheetVo::getPlannedQuantity,Comparator.reverseOrder())).limit(5).collect(Collectors.toList()));

        List<JobBookingVo> jobBookingNums = new ArrayList<>();
        List<JobBooking> allJobs = jobBookingMapper.selectList();

        Map<String, List<JobBooking>> jobGroupedByTaskName = allJobs.stream()
            .collect(Collectors.groupingBy(JobBooking::getProcedureName));

        jobGroupedByTaskName.forEach((taskName, group) -> {
            JobBookingVo jobBookingVo = new JobBookingVo();
            jobBookingVo.setProcedureName(taskName);
            jobBookingVo.setJobBookingCountNum((long) group.size());
            jobBookingNums.add(jobBookingVo);
        });
        result.put("jobBookingNums", jobBookingNums.stream().sorted(Comparator.comparing(JobBookingVo::getJobBookingCountNum,Comparator.reverseOrder())).limit(5).collect(Collectors.toList()));

        result.put("jobBookingList", allJobs.stream().sorted(Comparator.comparing(JobBooking::getCreateTime,Comparator.reverseOrder())).limit(50).collect(Collectors.toList()));

        result.put("sheetList",sheetService.queryList(new SheetBo()).stream().sorted(Comparator.comparing(SheetVo::getPlanStart,Comparator.reverseOrder())).collect(Collectors.toList()));

        return result;
    }
}
