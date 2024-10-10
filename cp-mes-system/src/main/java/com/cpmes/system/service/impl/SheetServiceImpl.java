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
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.SheetMaterial;
import com.cpmes.system.domain.Task;
import com.cpmes.system.mapper.SheetMaterialMapper;
import com.cpmes.system.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.SheetBo;
import com.cpmes.system.domain.vo.SheetVo;
import com.cpmes.system.domain.Sheet;
import com.cpmes.system.mapper.SheetMapper;
import com.cpmes.system.service.ISheetService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 工单Service业务层处理
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@RequiredArgsConstructor
@Service
public class SheetServiceImpl implements ISheetService {

    private final SheetMapper baseMapper;
    private final TaskMapper taskMapper;
    private final SheetMaterialMapper sheetMaterialMapper;

    /**
     * 查询工单
     */
    @Override
    public SheetVo queryById(Long sheetId) {
        SheetVo result = baseMapper.selectVoById(sheetId);
        List<Task> taskList = taskMapper.selectList(new LambdaQueryWrapper<Task>().eq(Task::getSheetId, result.getSheetId()).orderByAsc(Task::getSequence));
        List<SheetMaterial> sheetMaterials = sheetMaterialMapper.selectList(new LambdaQueryWrapper<SheetMaterial>().eq(SheetMaterial::getSheetId, result.getSheetId()).orderByAsc(SheetMaterial::getSequence));
        List<String> starts = StringUtils.splitList(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,result.getPlanStart())," ");
        result.setPlanStart(DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,result.getPlanStart())));
        result.setSelectStart(starts.get(1).substring(0,5));
        List<String> ends = StringUtils.splitList(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,result.getPlanEnd())," ");
        result.setPlanEnd(DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,result.getPlanEnd())));
        result.setSelectEnd(ends.get(1).substring(0,5));
        result.setTasks(taskList);
        result.setMaterials(sheetMaterials);
        return result;
    }

    /**
     * 查询工单(移动端)
     */
    @Override
    public SheetVo queryByIdMobile(Long sheetId) {
        return baseMapper.selectVoById(sheetId);
    }
    @Override
    public SheetVo queryBySheetNumber(String SheetNumber) {
        SheetVo result = baseMapper.selectVoOne(new LambdaQueryWrapper<Sheet>().eq(Sheet::getSheetNumber, SheetNumber));
        List<Task> taskList = taskMapper.selectList(new LambdaQueryWrapper<Task>().eq(Task::getSheetId, result.getSheetId()).orderByAsc(Task::getSequence));
        List<String> starts = StringUtils.splitList(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,result.getPlanStart())," ");
        result.setPlanStart(DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,result.getPlanStart())));
        result.setSelectStart(starts.get(1).substring(0,5));
        List<String> ends = StringUtils.splitList(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,result.getPlanEnd())," ");
        result.setPlanEnd(DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,result.getPlanEnd())));
        result.setSelectEnd(ends.get(1).substring(0,5));
        result.setTasks(taskList);
        return result;
    }

    /**
     * 查询工单列表
     */
    @Override
    public TableDataInfo<SheetVo> queryPageList(SheetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Sheet> lqw = buildQueryWrapper(bo);
        Page<SheetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<String> sheetIds = result.getRecords().stream().map(s->String.valueOf(s.getSheetId())).collect(Collectors.toList());
        if(ObjectUtil.isNotEmpty(sheetIds)) {
            List<Task> allTaskList = taskMapper.selectList(new LambdaQueryWrapper<Task>().in(Task::getSheetId, sheetIds));
            for (SheetVo vo : result.getRecords()) {
                List<Task> taskList = allTaskList.stream().filter(t -> t.getSheetId().equals(String.valueOf(vo.getSheetId()))).sorted(Comparator.comparing(Task::getSequence)).collect(Collectors.toList());
                vo.setTasks(taskList);
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询工单列表
     */
    @Override
    public List<SheetVo> queryList(SheetBo bo) {
        LambdaQueryWrapper<Sheet> lqw = buildQueryWrapper(bo);
        List<SheetVo> result = baseMapper.selectVoList(lqw);
        List<String> sheetIds = result.stream().map(s->String.valueOf(s.getSheetId())).collect(Collectors.toList());
        if(ObjectUtil.isNotEmpty(sheetIds)) {
            List<Task> allTaskList = taskMapper.selectList(new LambdaQueryWrapper<Task>().in(Task::getSheetId, sheetIds));
            for (SheetVo vo : result) {
                List<Task> taskList = allTaskList.stream().filter(t -> t.getSheetId().equals(String.valueOf(vo.getSheetId()))).sorted(Comparator.comparing(Task::getSequence)).collect(Collectors.toList());
                vo.setTasks(taskList);
            }
        }
        return result;
    }

    private LambdaQueryWrapper<Sheet> buildQueryWrapper(SheetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Sheet> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getSheetNumber()), Sheet::getSheetNumber, bo.getSheetNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getProductNumber()), Sheet::getProductNumber, bo.getProductNumber());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), Sheet::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getProductSpecification()), Sheet::getProductSpecification, bo.getProductSpecification());
        lqw.eq(StringUtils.isNotBlank(bo.getUnit()), Sheet::getUnit, bo.getUnit());
        lqw.eq(bo.getPlanStart() != null, Sheet::getPlanStart, bo.getPlanStart());
        lqw.eq(bo.getPlanEnd() != null, Sheet::getPlanEnd, bo.getPlanEnd());
        lqw.eq(bo.getPlannedQuantity() != null, Sheet::getPlannedQuantity, bo.getPlannedQuantity());
        lqw.eq(StringUtils.isNotBlank(bo.getRelatedDocuments()), Sheet::getRelatedDocuments, bo.getRelatedDocuments());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), Sheet::getStatus, bo.getStatus());
        lqw.in(ObjectUtil.isNotEmpty(bo.getQueryStatus()), Sheet::getStatus, bo.getQueryStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getQrcode()), Sheet::getQrcode, bo.getQrcode());
        lqw.eq(StringUtils.isNotBlank(bo.getClientId()), Sheet::getClientId, bo.getClientId());
        lqw.eq(StringUtils.isNotBlank(bo.getClientName()), Sheet::getClientName, bo.getClientName());
        lqw.between(bo.getParams().get("startTime") != null && bo.getParams().get("endTime") != null, Sheet::getPlanStart,bo.getParams().get("startTime"),bo.getParams().get("endTime"));
        return lqw;
    }

    /**
     * 新增工单
     */
    @Override
    public Boolean insertByBo(SheetBo bo) {
        Sheet add = BeanUtil.toBean(bo, Sheet.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (ObjectUtil.isNotEmpty(bo.getTasks())) {
            for (Task task : bo.getTasks()) {
                task.setSheetId(add.getSheetId().toString());
            }
            for (Task task : bo.getTasks()){
                task.setCreateTime(null);
                task.setCreateBy(null);
                task.setUpdateBy(null);
                task.setUpdateTime(null);
            }
            taskMapper.insertOrUpdateBatch(bo.getTasks());
        }
        if (ObjectUtil.isNotEmpty(bo.getMaterials())) {
            for (SheetMaterial sheetMaterial : bo.getMaterials()) {
                sheetMaterial.setSheetId(add.getSheetId().toString());
            }
            for (SheetMaterial sheetMaterial : bo.getMaterials()){
                sheetMaterial.setCreateTime(null);
                sheetMaterial.setCreateBy(null);
                sheetMaterial.setUpdateBy(null);
                sheetMaterial.setUpdateTime(null);
            }
            sheetMaterialMapper.insertOrUpdateBatch(bo.getMaterials());
        }
        if (flag) {
            bo.setSheetId(add.getSheetId());
        }
        return flag;
    }

    /**
     * 修改工单
     */
    @Override
    public Boolean updateByBo(SheetBo bo) {
        Sheet update = BeanUtil.toBean(bo, Sheet.class);
        String updateTaskIds= "";
        String updateMaterialIds = "";
        if (ObjectUtil.isNotEmpty(bo.getTasks())) {
            updateTaskIds = StringUtils.join(bo.getTasks().stream().map(Task::getTaskId).collect(Collectors.toList()), ",");
        }
        List<Task> currentTasks = taskMapper.selectList(new LambdaQueryWrapper<Task>().eq(Task::getSheetId, bo.getSheetId()));
        for (Task task : currentTasks) {
            if (!updateTaskIds.contains(task.getTaskId().toString())) {
                taskMapper.deleteById(task.getTaskId());
            }
        }
        if (ObjectUtil.isNotEmpty(bo.getTasks())) {
            for (Task task : bo.getTasks()) {
                task.setSheetId(bo.getSheetId().toString());
            }
            taskMapper.insertOrUpdateBatch(bo.getTasks());
        }
        if(ObjectUtil.isNotEmpty(bo.getMaterials())){
             updateMaterialIds = StringUtils.join(bo.getMaterials().stream().map(SheetMaterial::getAttachmentId).collect(Collectors.toList()), ",");
        }
        List<SheetMaterial> currentMaterials = sheetMaterialMapper.selectList(new LambdaQueryWrapper<SheetMaterial>().eq(SheetMaterial::getSheetId, bo.getSheetId()));
        for (SheetMaterial sheetMaterial : currentMaterials) {
            if (!updateMaterialIds.contains(sheetMaterial.getAttachmentId().toString())) {
                sheetMaterialMapper.deleteById(sheetMaterial.getAttachmentId());
            }
        }
        if (ObjectUtil.isNotEmpty(bo.getMaterials())) {
            for (SheetMaterial sheetMaterial : bo.getMaterials()) {
                sheetMaterial.setSheetId(bo.getSheetId().toString());
            }
            sheetMaterialMapper.insertOrUpdateBatch(bo.getMaterials());
        }
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Sheet entity) {
        //TODO 做一些数据校验,如唯一约束
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<Sheet>().eq(Sheet::getSheetNumber,entity.getSheetNumber())
            .ne(ObjectUtil.isNotNull(entity.getSheetId()),Sheet::getSheetId, entity.getSheetId()));
        if (exist){
            throw new ServiceException("工单编号已存在");
        }

    }

    /**
     * 批量删除工单
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        List<Task> tasks = taskMapper.selectList(new LambdaQueryWrapper<Task>().in(Task::getSheetId,ids));
        if (ObjectUtil.isNotEmpty(tasks)) {
            taskMapper.deleteBatchIds(tasks.stream().map(Task::getTaskId).collect(Collectors.toList()));
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
